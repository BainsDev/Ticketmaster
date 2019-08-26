import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.util.List;

public class Solver extends MyDriver {

    public static void main(String[] args) {

        try {

            String normalWin = "Your team has received the win for this match as you have provided valid proof of winning this match.";
            String normalLoss = "Your team has received the loss for this match as the opposing team has provided valid proof of winning this match.";
            String alreadyWon = "Your team has already received the win for this match.";
            String noTicketW = "As the opposing team failed to submit a Dispute Ticket within the time limit given, you have received the win for this match.";
            String noTicketL = "As your team failed to submit a ticket within the 2 hour time frame, the match outcome will remain. It's the responsibility of the winning team to report the score correctly.";
            String acceptWin = "Your team has received the win as the opposing team has accepted the loss for this match";
            String acceptLoss = "Your team has received the loss for this match as requested.";
            String forfeitW = "Your team has received the win for this match as you have provided valid proof of the opposing team forfeiting this match.";
            String forfeitL = "Your team has received the loss for this match as the opposing team has provided valid proof of you forfeiting this match.";
            String noShowWin = "Your team has received the win for this match as you have provided valid proof of your opponent not showing up to play within the 15 minutes they are allowed.";
            String noShowLoss = "Your team has received the loss for this match as your opponent has provided valid proof of your team not showing up within the 15 minute time limit.";
            String cancel = "I have decided to cancel this match since neither you or your opponent managed to submit valid proof for this match.";
            String unfinished = "This match was cancelled due to the fact that it was not completed";
            String lagCancel = "I have decided to cancel this match due to lag or connection issues that have affected the ability to fairly complete this match";
            String bothWin = "I have decided to cancel this match as both teams have provided valid proof of winning this match.";

            MyDriver.setUp();

            driver.get("https://umggaming.com/admin/support/ticket/new/all/system/elite");

            JFrame frame = new JFrame("Hi,");
            String code = JOptionPane.showInputDialog(
                    frame,
                    "Please select a row.",
                    "Welcome to the Run",
                    JOptionPane.QUESTION_MESSAGE
            );

            frame.dispose();

            int location = Integer.parseInt(code);

            MyDriver.location = location;

            while (true) {

                driver.findElement(By.xpath("/html/body/div/div/section[2]/div/div/div/div/div[2]/table/tbody/tr[" + location + "]/td[4]")).click();

                List<WebElement> commentBoxes = driver.findElements(By.xpath("//button[contains(.,'Add Comment')]"));

                String firstTicketUser = driver.findElement(By.xpath("/html/body/div/div/section[2]/div/div/div[2]/div/div/div/div[2]/div/p/a")).getText();
                String team1 = driver.findElement(By.xpath("/html/body/div/div/section[2]/div/div/div/div/div/div[2]/span/a")).getText();
                String team2 = driver.findElement(By.xpath("/html/body/div/div/section[2]/div/div/div/div/div/div[3]/span/a")).getText();
                String firstTicketTeam = null;
                String response = null;
                String result = null;

                if (driver.findElement(By.xpath("/html/body/div/div/section[2]/div/div/div/div/div/div[3]")).getText().contains(firstTicketUser))
                    firstTicketTeam = driver.findElement(By.xpath("/html/body/div/div/section[2]/div/div/div/div/div/div[3]/span/a")).getText();
                else
                    firstTicketTeam = driver.findElement(By.xpath("/html/body/div/div/section[2]/div/div/div/div/div/div[2]/span/a")).getText();

                commentBoxes.clear();

                driver.switchTo().frame(driver.findElement(By.className("wysihtml5-sandbox")));

                WebElement editor = driver.findElement(By.tagName("body"));

                (new WebDriverWait(driver, 9999)).until(new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver d) {
                        return editor.getText().length() > 10;
                    }
                });

                if (editor.getText().contains("received the win"))
                {
                    if (editor.getText().contains(normalWin)) {
                        response = "Normal Win";
                    } else if (editor.getText().contains(alreadyWon)) {
                        response = "Already Won";
                    } else if (editor.getText().contains(noTicketW)) {
                        response = "No Ticket";
                    } else if (editor.getText().contains(acceptWin)) {
                        response = "Accept Win";
                    } else if (editor.getText().contains(forfeitW)) {
                        response = "Forfeit Win";
                    } else if (editor.getText().contains(noShowWin)) {
                        response = "No Show Win";
                    }
                    result = "win";
                }
                else if (editor.getText().contains("received the loss"))
                {
                    if (editor.getText().contains(normalLoss)) {
                        response = "Normal Loss";
                    } else if (editor.getText().contains(noTicketL)) {
                        response = "No Ticket L";
                    } else if (editor.getText().contains(acceptLoss)) {
                        response = "Accept Loss";
                    } else if (editor.getText().contains(forfeitL)) {
                        response = "Forfeit Loss";
                    } else if (editor.getText().contains(noShowLoss)) {
                        response = "No Show L";
                    }
                    result = "loss";
                }
                else if (editor.getText().contains("cancel")) {
                    if (editor.getText().contains(cancel)) {
                        response = "Cancel";
                    } else if (editor.getText().contains(unfinished)) {
                        response = "Unfinished";

                    } else if (editor.getText().contains(lagCancel)) {
                        response = "Lag 30+ Sec";
                    } else if (editor.getText().contains(bothWin)) {
                        response = "2 Proof";
                    }
                    result = "cancel";
                }

                driver.switchTo().defaultContent();

                JOptionPane.showMessageDialog(frame,
                        "Please press OK to continue after you have chosen and added a reason to your response");

                driver.findElement(By.xpath("//button[contains(.,'Add Comment')]")).click();

                commentBoxes = driver.findElements(By.xpath("//button[contains(.,'Add Comment')]"));

                if (commentBoxes.size() == 2) {
                    driver.findElement(By.xpath("//input[@value='" + response + "']")).click();
                    JOptionPane.showMessageDialog(frame,
                            "Please press OK to continue after you have chosen and added a reason to your response");
                    commentBoxes.get(1).click();
                }

                if (result == "win") {
                    driver.findElement(By.xpath("//button[contains(.," + firstTicketTeam + ")]")).click();
                    driver.findElement(By.id("dataConfirmOKsuccess")).click();
                }
                else if (result == "loss") {

                    if (team1.equals(firstTicketTeam))
                        driver.findElement(By.xpath("//button[contains(.," + team2 + ")]")).click();

                    else if (team2.equals(firstTicketTeam))
                        driver.findElement(By.xpath("//button[contains(.," + team1 + ")]")).click();

                    driver.findElement(By.id("dataConfirmOKsuccess")).click();
                }
                else if (result == "cancel") {
                    driver.findElement(By.xpath("//button[contains(.,'Cancel')]")).click();
                    driver.findElement(By.id("dataConfirmOKdanger")).click();
                }

                WebElement mySelectElement = driver.findElement(By.id("status"));
                Select dropdown = new Select(mySelectElement);
                dropdown.selectByVisibleText("Closed");
                driver.findElement(By.xpath("/html/body/div/div/section[2]/div/div[2]/form/button")).click();
            }

        }

        finally {
            driver.quit();
        }
            }

    }

