package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

public class InputValidator {

    public InputValidator() {
    }

    public static boolean validateBookAdd(String title, List<String> authorsList, String genre,
                                          LocalDate publicationDate){
        if(title == "" || title == null) {
            return false;
        }

        if(authorsList.size() == 0) {
            return false;
        }

        if(genre == "" || genre == null) {
            return false;
        }

        if(publicationDate == null) {
            return false;
        }else {
            SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-mm-dd");
            sdfrmt.setLenient(false);
            try {
                Date javaDate = sdfrmt.parse(publicationDate.toString());
            } catch (DateTimeParseException | ParseException e) {
                return false;
            }
        }

        return true;
    }
}
