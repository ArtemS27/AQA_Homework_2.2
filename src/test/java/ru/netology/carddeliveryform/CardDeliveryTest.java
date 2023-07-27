package ru.netology.carddeliveryform;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {
    String dateUpFourDays = generateDate(4, "dd.MM.yyyy");
    String dateUpSevenDays = generateDate(7, "dd.MM.yyyy");
    String day = generateDate(7, "d");
    String month = generateDate(7, "MM");
    public String generateDate(long addDay, String pattern){
        return LocalDate.now().plusDays(addDay).format(DateTimeFormatter.ofPattern(pattern));
    }
    public void changeMonth(){
        String currentMonth = generateDate(0, "MM");
        if(Integer.parseInt(month)>Integer.parseInt(currentMonth)){
            $$(".popup_visible .calendar__arrow_direction_right").last().click();
        }
    }

    @Test
    public void shouldSendForm(){
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Москва");
        form.$("[data-test-id=date] input").doubleClick();
        form.$("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(dateUpFourDays);
        form.$("[data-test-id=name] input").setValue("Иванов Иван");
        form.$("[data-test-id=phone] input").setValue("+71234567890");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        form.$(".spin_visible").shouldBe(Condition.visible  );
        $("[data-test-id=notification]").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id=notification] .notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + dateUpFourDays), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

    @Test
    public void choseCityAndDateFromPopUpMenu(){
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Мо");
        $$(".popup_visible .menu-item").find(Condition.exactText("Москва")).click();
        form.$("[data-test-id=date] .icon-button").click();
        changeMonth();
        $$(".popup_visible .calendar__day").find(Condition.exactText(day)).click();
        form.$("[data-test-id=name] input").setValue("Иванов Иван");
        form.$("[data-test-id=phone] input").setValue("+71234567890");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        form.$(".spin_visible").shouldBe(Condition.visible);
        $("[data-test-id=notification]").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id=notification] .notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + dateUpSevenDays), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }
}
