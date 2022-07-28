/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author Admin
 */
public class Pagination {

    private static final int NUMBER_ITEMS_PER_PAGE = 5;

    public static int getNumberPage(int total) {
        int numberPage;
        if (total % NUMBER_ITEMS_PER_PAGE == 0) {
            numberPage = total / NUMBER_ITEMS_PER_PAGE;
        } else {
            numberPage = total / NUMBER_ITEMS_PER_PAGE + 1;
        }
        return numberPage;
    }

    public static int getStart(int pageNumber) {
        return (pageNumber - 1) * NUMBER_ITEMS_PER_PAGE;
    }

    public static int getEnd(int pageNumber, int total) {
        return Math.min(pageNumber * NUMBER_ITEMS_PER_PAGE, total);
    }
}
