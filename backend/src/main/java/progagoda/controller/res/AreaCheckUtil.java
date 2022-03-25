package progagoda.controller.res;

public class AreaCheckUtil {
    public AreaCheckUtil() {
    }

    public static float left(float x) {
        float hor = x * 90.0F + 500.0F;
        return hor / 1000.0F;
    }

    public static float top(float y) {
        float ver = 500.0F - y * 90.0F;
        return ver / 1000.0F;
    }

    public static boolean isIn(float x, float y, float r) {
        return isCircle(x, y, r) || isRect(x, y, r) || isTriangle(x, y, r);
    }

    private static boolean isTriangle(float x, float y, float r) {
        return x <= 0.0F && y <= 0.0F && (double)y + 0.5D * (double)x + (double)(r / 2.0F) >= 0.0D;
    }

    private static boolean isCircle(float x, float y, float r) {
        return x >= 0.0F && y >= 0.0F && x * x + y * y <= r * r;
    }

    private static boolean isRect(float x, float y, float r) {
        return x >= 0.0F && y <= 0.0F && x <= r && y >= -1.0F * r / 2.0F;
    }
}
