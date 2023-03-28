package com.inalogy.idm.utilities.expander;

import org.testng.annotations.Test;

public class TestMain {

    @Test
    public void printHelp() {
        String[] input = new String[]{"-h"};
        Main.main(input);
    }

    @Test
    public void replace() {
        String[] input = new String[]{
                "expand",
                "-w", "./workfiles/xmls",
                "-e", "xml",
                "-p", "./workfiles/arbitrary.properties"
        };
        Main.main(input);
    }
}
