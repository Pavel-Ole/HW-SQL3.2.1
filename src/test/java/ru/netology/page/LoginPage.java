package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;
import static ru.netology.data.DataHelper.getLoginInfo;
import static ru.netology.data.DataHelper.getRandomPassword;

public class LoginPage {
    public VerificationPage validLogin(DataHelper.LoginInfo info) {
        $("[data-test-id=login] input").setValue(info.getLogin());
        $("[data-test-id=password] input").setValue(info.getPassword());
        $("[data-test-id=action-login]").click();
        return new VerificationPage();
    }

    public void invalidPasswordAssert(DataHelper.LoginInfo info) {
        $("[data-test-id=login] input").setValue(info.getLogin());
        $("[data-test-id=password] input").setValue(getRandomPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error_notification]").shouldBe(Condition.visible);
    }

    public void clearPage() {
        $("[data-test-id=login] input").doubleClick();
        $("[data-test-id=login] input").sendKeys(Keys.DELETE);
        $("[data-test-id=password] input").doubleClick();
        $("[data-test-id=password] input").sendKeys(Keys.DELETE);
    }

    public void tooManyAttemptsAssert(DataHelper.LoginInfo info) {
        int x = 0;
        for (int i = 0; i < 4; i++) {
            if (i < 3) {
                $("[data-test-id=login] input").setValue(info.getLogin());
                $("[data-test-id=password] input").setValue(getRandomPassword());
                $("[data-test-id=action-login]").click();
                $("[data-test-id=login] input").doubleClick();
                $("[data-test-id=login] input").sendKeys(Keys.DELETE);
                $("[data-test-id=password] input").doubleClick();
                $("[data-test-id=password] input").sendKeys(Keys.DELETE);

                x++;
            } else {

                $("[data-test-id=error_notification] .notification__content").getText().equals("Ошибка! " +
                        "Превышено количество попыток входа. Пользователь заблокирован");
            }
        }
    }
}
