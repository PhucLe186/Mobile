package com.example.projectmobile.Module;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import java.util.Random;

public class CaptchaGenerator {
    private static final char[] CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
    private static final int WIDTH = 300;
    private static final int HEIGHT = 100;
    private static final int CODE_LENGTH = 5;

    private String captchaCode;
    private Random random = new Random();

    //    Draw a random captcha image
    public Bitmap createCaptchaBitmap() {
        captchaCode = generateCode();

        Bitmap bitmap = Bitmap.createBitmap(WIDTH, HEIGHT, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(60);
        paint.setTypeface(Typeface.DEFAULT_BOLD);

//      Draw text on bitmap
        for (int i = 0; i < captchaCode.length(); i++) {
            paint.setColor(generateRandomColor());
            float x = 40 + i * 50;
            float y = 70 + random.nextInt(20);
            canvas.save();
            float rotate = random.nextInt(30) - 15;
            canvas.rotate(rotate, x, y);
            canvas.drawText(String.valueOf(captchaCode.charAt(i)), x, y, paint);
            canvas.restore();
        }

//      Draw stroke on bitmap
        for (int i = 0; i < 8; i++) {
            paint.setColor(generateRandomColor());
            paint.setStrokeWidth(3);
            float startX = random.nextInt(WIDTH);
            float startY = random.nextInt(HEIGHT);
            float stopX = random.nextInt(WIDTH);
            float stopY = random.nextInt(HEIGHT);
            canvas.drawLine(startX, startY, stopX, stopY, paint);
        }
        return bitmap;
    }

    //    Generate a random String base on CHARS
    private String generateCode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(CHARS[random.nextInt(CHARS.length)]);
        }
        return sb.toString();
    }

    //    Generate a random color
    private int generateRandomColor() {
        return Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    //    Return captcha code value
    public String getCaptchaCode() {
        return captchaCode;
    }
}
