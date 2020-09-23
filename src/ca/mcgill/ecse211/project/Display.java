package ca.mcgill.ecse211.project;

import static ca.mcgill.ecse211.project.Resources.*;
import java.text.DecimalFormat;

/**
 * This class is used to display the content of the odometer variables (x, y, theta).
 */
public class Display implements Runnable {

  private double[] position;
  private static final long DISPLAY_PERIOD = 25;
  private long timeout = Long.MAX_VALUE;
/*
*The run() method for the Display class
*@return void 
*/
  public void run() {

    lcd.clear();

    long updateStart;
    long updateEnd;

    long startTime = System.currentTimeMillis();
    do {
      updateStart = System.currentTimeMillis();

      // Retrieve x, y and Theta information
      position = odometer.getXyt();

      // Print x,y, and theta information
      DecimalFormat numberFormat = new DecimalFormat("######0.00");
      lcd.drawString("X: " + numberFormat.format(position[0]), 0, 0);
      lcd.drawString("Y: " + numberFormat.format(position[1]), 0, 1);
      lcd.drawString("T: " + numberFormat.format((position[2] * 180)/Math.PI), 0, 2);
      
      lcd.drawString("Left Motor: " + leftMotor.getSpeed(), 0, 3);
      lcd.drawString("Right Motor: " + rightMotor.getSpeed(), 0, 4);

      // this ensures that the data is updated only once every period
      updateEnd = System.currentTimeMillis();
      if (updateEnd - updateStart < DISPLAY_PERIOD) {
        Main.sleepFor(DISPLAY_PERIOD - (updateEnd - updateStart));
      }
    } while ((updateEnd - startTime) <= timeout);

  }

  /**
   * Sets the timeout in ms.
   * 
   * @param timeout the timeout in milliseconds
   *@return void
   */
  public void setTimeout(long timeout) {
    this.timeout = timeout;
  }

  /**
   * Shows the text on the LCD, line by line.
   * 
   *@param strings comma-separated list of strings, one per line
   *@return void 
   */
  public static void showText(String... strings) {
    lcd.clear();
    for (int i = 0; i < strings.length; i++) {
      lcd.drawString(strings[i], 0, i);
    }
  }

}
