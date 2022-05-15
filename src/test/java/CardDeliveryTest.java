import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;


public class CardDeliveryTest {
    LocalDate currentDate = LocalDate.now();
    @Test
    void shouldSubmitRequest(){
        LocalDate date = currentDate.plusDays(14);
        String appointmentDate = date.format(DateTimeFormatter.ofPattern("dd.MM.uuuu"));
        open("http://localhost:9999");
        $("[placeholder=Город]").setValue("Омск");
        $("[placeholder=\"Дата встречи\"]").setValue(appointmentDate);
        $(byName("name")).setValue("Цзяо Екатерина");
        $(byName("phone")).setValue("+71234567890");
        $(".checkbox").click();
        $(".grid-row button").click();
        $(".notification__title").should(ownText("Успешно!"), Duration.ofSeconds(15));
        $(".notification__content").should(ownText("Встреча успешно забронирована на \r\n "+
        "28.05.2022"), Duration.ofSeconds(15));
    }
}
