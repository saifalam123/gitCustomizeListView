package com.example.appnumber34;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomizeListView extends BaseAdapter {

    //Base Adapter is a asbstract Class!
    Context myContext; // Yeh bhi ek class hai jis ka kaam intent or activities waghera ko le kar ama hota hai
    LayoutInflater myLayoutInFlator; // Is ka kaam yeh hai kay jo Customize_list_view hai uss main jo bhi cheese hai usko Content_main mein INFLATE karna hai.

    // AnimalImage AnimalName AnimalSpeed AnimalPower array
    // Download images of animals

    int[] animalImages = {R.drawable.bear , R.drawable.bird , R.drawable.cat , R.drawable.cow  ,R.drawable.dolphin
    ,R.drawable.fish  ,R.drawable.fox , R.drawable.horse , R.drawable.lion , R.drawable.tiger};
    String [] animalNames = {"Bear" , "Bird" , "Cat" , "Cow" , "Dolphin" , "Fish" , "Fox" , "Horse" , "Lion" , "Tiger"};
    int[] animalSpeed = {50 , 80 , 60 , 10 , 50 , 40 , 80 , 350 , 200 , 100};
    int[] animalPower = {200 , 20 , 50 , 150 , 50 , 10 , 70 , 400 , 250 , 220};

    public CustomizeListView(Context myContext)
    {
        this.myContext  = myContext;
        myLayoutInFlator = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE); // Yahan pey hum uska INFLATER service ko use kar rahe hain,  uper tou sirf declare kara tha
    }
    //Abstract methods
    @Override
    public int getCount() {
        return animalImages.length;
    }

    @Override
    public Object getItem(int position) {
        return animalImages[position]; //yeh uss position pey jo image hogi usko le kar ayega! Yahan pey INput diya jaa raha hai as a POSITION
    }

    @Override
    public long getItemId(int position) {
        return position;
    } // Yeh uss position ki ID ko le kar ayega , array number I guess!

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        view = myLayoutInFlator.inflate(R.layout.customize_list_view , null); //inflate karo customizeviewlist ko view object main.

        //Yahan saare kaam aab view. kar kay ho rahe ho kyun kay view ko inflate karna hai main activity kay sath

        ImageView imgAnimal = (ImageView) view.findViewById(R.id.imgAnimal);
        final TextView txtAnimalName = (TextView) view.findViewById(R.id.txtAnimalName);
        final TextView txtAnimalPower = (TextView) view.findViewById(R.id.txtAnimalPower);
        final TextView txtAnimalSpeed = (TextView) view.findViewById(R.id.txtAnimalSpeed);

        String oldtxtAnimalName = txtAnimalName.getText() + "";
        String oldtxtAniamlPower = txtAnimalPower.getText() + "";
        String oldtxtAnimalSpeed = txtAnimalSpeed.getText() + "";

        /*imgAnimal.setImageResource(animalImages[position]); */ // by doing so memory will waste so we do another work!


        //By doing so we can save the memory but it decode the quality of our images options.inSampleSize = 4 this 4 decrease the the size and if I put 8 it decreases more
        /*final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        Bitmap bm = BitmapFactory.decodeResource(myContext.getResources() , animalImages[position] , options);
        imgAnimal.setImageBitmap(bm); */

        imgAnimal.setImageBitmap(decodeSampleBitmapFromResource(myContext.getResources() , animalImages[position] , 50 , 50));
        txtAnimalName.setText(oldtxtAniamlPower + animalNames[position]);
        txtAnimalPower.setText(oldtxtAniamlPower + animalPower[position]);
        txtAnimalSpeed.setText(oldtxtAnimalSpeed + animalSpeed[position]);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(myContext , txtAnimalName.getText() + "\n" + txtAnimalPower.getText() + "\n" + txtAnimalSpeed.getText() , Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    // Yahan se jo bhi kaam hua hai wo picture ki wajha se jo memory waste hou rahi thi usko control karne kay liye kiya hai!
    public static int calculateInsampleSize(BitmapFactory.Options options , int reqWidth  , int reqHeight)
    {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth)
        {
            final int halfheight = height / 2;
            final int halfwidth = width / 2;
            while ((halfheight / inSampleSize) > reqHeight && (halfwidth/inSampleSize) > reqWidth)
            {
                inSampleSize *= 2;
            }
        }
        return  inSampleSize;
    }
    public static Bitmap decodeSampleBitmapFromResource(Resources res , int resID , int reqWidth , int reqHeight)
    {

        final BitmapFactory.Options options = new BitmapFactory.Options();

        // Getting dimension by setting it true!
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res , resID , options);

        options.inSampleSize = calculateInsampleSize(options , reqWidth , reqHeight);

        //Getting Images by setting it to false
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res , resID , options);
    }
}
