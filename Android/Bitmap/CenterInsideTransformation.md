# Glide头像缩放

    public class CenterInsideTransformation extends BitmapTransformation {
        public CenterInsideTransformation(Context context) {
            super(context);
        }

        public CenterInsideTransformation(BitmapPool bitmapPool) {
            super(bitmapPool);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            final Bitmap toReuse = pool.get(outWidth, outHeight, toTransform.getConfig() != null
                    ? toTransform.getConfig() : Bitmap.Config.ARGB_8888);
            Bitmap transformed = centerInside(toReuse, toTransform, outWidth, outHeight);
            return transformed;
        }

        @Override
        public String getId() {
            return "CenterInsideTransformation.com.bumptech.glide.load.resource.bitmap";
        }

        public static Bitmap centerInside(Bitmap recycle, Bitmap toCenter, int width, int height) {
            if (toCenter == null) {
                return null;
            }
            if (toCenter.getWidth() == width && toCenter.getHeight() == height) {
                return toCenter;
            }
            Matrix matrix = new Matrix();
            matrix.setScale(width * 1.0f / toCenter.getWidth(), height * 1.0f / toCenter.getHeight());
            final Bitmap result;
            if (recycle != null) {
                result = recycle;
            } else {
                result = Bitmap.createBitmap(width, height, toCenter.getConfig() != null ? toCenter
                        .getConfig() : Bitmap.Config.ARGB_8888);
            }
            result.setHasAlpha(toCenter.hasAlpha());
            Paint paint = new Paint(Paint.DITHER_FLAG | Paint.FILTER_BITMAP_FLAG);
            Canvas canvas = new Canvas(result);
            canvas.drawBitmap(toCenter, matrix, paint);
            return result;
        }
    }