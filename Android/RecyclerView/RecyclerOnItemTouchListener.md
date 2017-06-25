# RecyclerView点击和长按事件

    public class RecyclerOnItemTouchListener implements RecyclerView.OnItemTouchListener {
        private GestureDetector detector;
        private OnTouchListener listener;

        public RecyclerOnItemTouchListener(final RecyclerView recyclerView, OnTouchListener
                listener) {
            this.listener = listener;
            detector = new GestureDetector(recyclerView.getContext(), new GestureDetector
                    .SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (childView != null && RecyclerOnItemTouchListener.this.listener != null) {
                        RecyclerOnItemTouchListener.this.listener.onItemLongClick(recyclerView,
                                recyclerView.getChildAdapterPosition(childView));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View childView = rv.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && listener != null && detector.onTouchEvent(e)) {
                listener.onItemClick(childView, rv.getChildAdapterPosition(childView));
                return true;
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }

        public static abstract class OnTouchListener {
            public void onItemClick(View view, int position) {
            }

            public void onItemLongClick(View view, int position) {
            }
        }
    }