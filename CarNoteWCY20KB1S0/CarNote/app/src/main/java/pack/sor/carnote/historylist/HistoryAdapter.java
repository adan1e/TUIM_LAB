package pack.sor.carnote.historylist;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;

import pack.sor.carnote.R;
import pack.sor.carnote.model.ViewHolderAdaptable;

/**
 * Adapter budujący elementy listy historii na podstawie historii auta
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>
{
    private Context context;
    private Drawable drawable;
    private ArrayList<ViewHolderAdaptable> list = new ArrayList();
    private HistoryRemover historyRemover;

    public HistoryAdapter(ArrayList<ViewHolderAdaptable> list, HistoryRemover historyRemover, Context context)
    {
        this.context = context;
        this.list = list;
        this.historyRemover = historyRemover;
        Collections.sort(list, (o1, o2) -> o2.getDate().compareTo(o1.getDate()));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_history_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        ViewHolderAdaptable item = list.get(position);
        drawable = context.getResources().getDrawable(item.getCategoryDrawable());
        holder.activityImageView.setImageDrawable(drawable);
        DateFormat dateFormat = DateFormat.getDateInstance();
        holder.leftLabelTopTextView.setText(dateFormat.format(item.getDate()));
        holder.rightLabelTopTextView.setText(String.format("%s KM", String.valueOf(item.getMileage())));
        holder.leftLabelBottomTextView.setText(item.getFirstText());
        holder.rightLabelBottomTextView.setText(item.getSecondText());
        holder.trashImageView.setOnClickListener(v ->
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Na pewno usuwamy " + holder.leftLabelBottomTextView.getText() + "?");
            builder.setPositiveButton("Usuń", (dialog, which) -> historyRemover.remove(list.remove(position)));
            builder.setNeutralButton("Zostaw", null);
            builder.create().show();
        });
    }

    @Override
    public int getItemCount()
    {
        if (list == null)
        {
            return 0;
        } else
        {
            return list.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        protected TextView leftLabelBottomTextView;
        protected TextView rightLabelBottomTextView;
        protected TextView leftLabelTopTextView;
        protected TextView rightLabelTopTextView;
        protected ImageView trashImageView;
        protected ImageView activityImageView;

        public ViewHolder(View itemView)
        {
            super(itemView);
            this.activityImageView = (ImageView) itemView.findViewById(R.id.activity_imageview);
            this.trashImageView = (ImageView) itemView.findViewById(R.id.trash_image_view);
            this.leftLabelBottomTextView = (TextView) itemView.findViewById(R.id.left_label_bottom);
            this.leftLabelTopTextView = (TextView) itemView.findViewById(R.id.left_label_top);
            this.rightLabelBottomTextView = (TextView) itemView.findViewById(R.id.right_label_bottom);
            this.rightLabelTopTextView = (TextView) itemView.findViewById(R.id.right_label_top);
        }
    }
}
