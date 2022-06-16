package org.apache.poi.examples;

/**
 * @author: zeting
 * @date: 2022/4/1
 */
/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */


import org.apache.commons.io.output.UnsynchronizedByteArrayOutputStream;
import org.apache.poi.examples.xssf.eventusermodel.XLSX2CSV;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;

import java.io.File;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class TestXLSX2CSV {
    private PrintStream err;
    private static final UnsynchronizedByteArrayOutputStream errorBytes = new UnsynchronizedByteArrayOutputStream();


    public static void main(String[] args) {
        try {
            testSampleFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUp() throws UnsupportedEncodingException {
        // remember and replace default error streams
        err = System.err;

        PrintStream error = new PrintStream(errorBytes, true, "UTF-8");
        System.setErr(error);
    }

    public void tearDown() {
        // restore output-streams again
        System.setErr(err);

        // Print out found error
        if (errorBytes.size() > 0) {
            System.err.println("Had stderr: " + errorBytes.toString(StandardCharsets.UTF_8));
        }
    }

    public void testNoArgument() throws Exception {
        // returns with some System.err
        XLSX2CSV.main(new String[0]);

        String output = errorBytes.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("XLSX2CSV <xlsx file>"), "Had: " + output);
    }

    public void testInvalidFile() throws Exception {
        // returns with some System.err
        XLSX2CSV.main(new String[]{"not-existing-file.xlsx"});

        String output = errorBytes.toString("UTF-8");
        assertTrue(output.contains("Not found or not a file: not-existing-file.xlsx"), "Had: " + output);
    }

    public static void testSampleFile() throws Exception {
        final UnsynchronizedByteArrayOutputStream outputBytes = new UnsynchronizedByteArrayOutputStream();
        PrintStream out = new PrintStream(outputBytes, true, "UTF-8");

        // The package open is instantaneous, as it should be.
//        String openFilePath = "./zt_poiCxcel/test-data/openxml4j/sample.xlsx";
        String openFilePath = "D:\\ZZTAndroid\\GitHub\\AppFrame7\\zt_poiCxcel\\test-data\\openxml4j\\sample.xlsx";
        File file = new File(openFilePath);
        OPCPackage p = OPCPackage.open(file, PackageAccess.READ);
        XLSX2CSV xlsx2csv = new XLSX2CSV(p, out, -1);
        xlsx2csv.process();


        String errorOutput = errorBytes.toString(StandardCharsets.UTF_8);
        assertEquals(errorOutput.length(), 0);

        String output = outputBytes.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("\"Lorem\",111"), "Had: " + output);
        assertTrue(output.contains(",\"hello, xssf\",,\"hello, xssf\""), "Had: " + output);
    }

    public void testMinColumns() throws Exception {
        final UnsynchronizedByteArrayOutputStream outputBytes = new UnsynchronizedByteArrayOutputStream();
        PrintStream out = new PrintStream(outputBytes, true, "UTF-8");

        // The package open is instantaneous, as it should be.
        String openFilePath = "./zt_poiCxcel/test-data/openxml4j/sample.xlsx";
        File file = new File(openFilePath);
        try (OPCPackage p = OPCPackage.open(file, PackageAccess.READ)) {
            XLSX2CSV xlsx2csv = new XLSX2CSV(p, out, 5);
            xlsx2csv.process();
        }

        String errorOutput = errorBytes.toString(StandardCharsets.UTF_8);
        assertEquals(errorOutput.length(), 0);

        String output = outputBytes.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("\"Lorem\",111,,,"), "Had: " + output);
        assertTrue(output.contains(",\"hello, xssf\",,\"hello, xssf\","), "Had: " + output);
    }

    private static void assertEquals(int length, int i) {
        System.out.println("length:" + length + " >>> i:" + i);
    }

    private static void assertTrue(boolean contains, String s) {
        System.out.println("contains:" + contains + " >>> " + s);
    }
}