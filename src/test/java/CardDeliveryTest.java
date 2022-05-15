import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {
    LocalDate currentDate = LocalDate.now();
    
    @BeforeEach
    void openWebsite() {
        open("http://localhost:9999");
    }
    
    @Test
    void shouldSubmitRequest() {
        LocalDate date = currentDate.plusDays(14);
        String appointmentDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder=Город]").setValue("Омск");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);//clear field
        $("[placeholder=\"Дата встречи\"]").setValue(appointmentDate);
        $(byName("name")).setValue("Цзяо Екатерина");
        $(byName("phone")).setValue("+71234567890");
        $(".checkbox").click();
        $(".grid-row button").click();
        $(".notification__title").should(ownText("Успешно!"), Duration.ofSeconds(15));
        $(".notification__content").should(ownText("Встреча успешно забронирована на \r\n" +
                appointmentDate), Duration.ofSeconds(15));
    }
    
    @Test
    void shouldUseCitySelector() {
        LocalDate date = currentDate.plusDays(5);
        String appointmentDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder=Город]").setValue("Ом");
        $x("//*[text()=\"Омск\"]").click();//choose city
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[placeholder=\"Дата встречи\"]").setValue(appointmentDate);
        $(byName("name")).setValue("Цзяо Екатерина");
        $(byName("phone")).setValue("+71234567890");
        $(".checkbox").click();
        $(".grid-row button").click();
        $(".notification__title").should(ownText("Успешно!"), Duration.ofSeconds(15));
        $(".notification__content").should(ownText("Встреча успешно забронирована на \r\n" +
                appointmentDate), Duration.ofSeconds(15));
    }
    
    @Test
    void shouldUseCalendar() {
        LocalDate date = currentDate.plusDays(7);
        String appointmentDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder=Город]").setValue("Омск");
        $(withText("Карта")).click();//close popup with cities
        $x("//*[@class=\"input__icon\"]").click();//open calendar
        if (date.getDayOfMonth() <= 7) {
            $(".calendar__arrow_direction_right[data-step=\"1\"]").click();
        }//in case date is in the next month
        $(byTagAndText("td", String.valueOf(date.getDayOfMonth()))).click();//choose date
        $(byName("name")).setValue("Цзяо Екатерина");
        $(byName("phone")).setValue("+71234567890");
        $(".checkbox").click();
        $(".grid-row button").click();
        $(".notification__title").should(ownText("Успешно!"), Duration.ofSeconds(15));
        $(".notification__content").should(ownText("Встреча успешно забронирована на \r\n" +
                appointmentDate), Duration.ofSeconds(15));
    }
}
