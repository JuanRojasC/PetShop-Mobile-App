package com.petshop.persistence.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.petshop.persistence.model.Product;
import com.petshop.persistence.model.Service;
import com.petshop.persistence.model.Store;
import com.petshop.persistence.service.ProductService;
import com.petshop.persistence.service.ServiceService;
import com.petshop.persistence.service.StoreService;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

public class DBQueries {

    @SuppressLint("StaticFieldLeak")
    private static DBQueries instance = null;
    private final ProductService productService;
    private final ServiceService serviceService;
    private final StoreService storeService;
    private final Context context;

    @RequiresApi(api = Build.VERSION_CODES.O)
    private DBQueries(Context context) {
        this.context = context;
        this.productService = new ProductService(context);
        this.serviceService = new ServiceService(context);
        this.storeService = new StoreService(context);
//        insertStores();
//        insertServices();
//        insertProducts();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static DBQueries getInstance(Context context){
        if(instance == null) instance = new DBQueries(context);
        return instance;
    }

    public Collection<String> getAllCreatesTables(){
        Collection<String> allQueries = new ArrayList<>();
        allQueries.add(CREATE_PRODUCTS_TABLE);
        allQueries.add(CREATE_SERVICES_TABLE);
        allQueries.add(CREATE_STORES_TABLE);
        allQueries.add(CREATE_FAVORITES_TABLE);
        return allQueries;
    }

    public Collection<String> getAllDeleteTables(){
        Collection<String> allQueries = new ArrayList<>();
        allQueries.add(DELETE_PRODUCTS_TABLE);
        allQueries.add(DELETE_SERVICES_TABLE);
        allQueries.add(DELETE_STORES_TABLE);
        return allQueries;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void insertProducts() {
        for(Product p : dataProducts()) {
            productService.insertProduct(p, null);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void insertServices(){
        for(Service s : dataServices())
            serviceService.insertService(s, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void insertStores(){
        for(Store s : dataStores())
            storeService.insertStore(s, null);
    }

    private byte[] convertImagesToBlob(String imageName){
        int imageId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        Drawable image = context.getResources().getDrawable(imageId);
        Bitmap bitmap = ((BitmapDrawable)image).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitMapData = stream.toByteArray();
        return bitMapData;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String convertImagesToString(String imageName){
        byte[] image = convertImagesToBlob(imageName);
        return Base64.getEncoder().encodeToString(image);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String convertImagesToString(byte[] image){
        return Base64.getEncoder().encodeToString(image);
    }

    // DATA
    private List<Product> dataProducts(){
        List<Product> data = new ArrayList<>();
        data.add(new Product(null,"Pelota", "Pelota con lazo de alta densidad, soporta mordiscos y grandes fuerzas, ideal para divertirte con tu mascota.", 23.999, 25.0, convertImagesToBlob("product_1")));
        data.add(new Product(null,"Comedero", "Dile adiós a los ladridos o maullidos por hambre, con este comedero minimalista el alimento nunca faltara.", 179.999, 17.0, convertImagesToBlob("product_2")));
        data.add(new Product(null,"Tunel Gatuno", "Entro y salgo, un gato podría pasar horas jugando con este sencillo pero entretenido túnel de alta resistencia.", 89.999, 21.0, convertImagesToBlob("product_3")));
        data.add(new Product(null,"Ratones Gatunos", "Porque no tener un Tom y dos Jerrys, con estos hermosos ratoncitos de juguete para tu león domestico.", 29.999, 43.0, convertImagesToBlob("product_4")));
        data.add(new Product(null,"Bebedero Portable", "La correcta hidratación de tus mascotas es importante, por eso dile adiós a los problemas con este termo portable.", 79.999, 36.0, convertImagesToBlob("product_5")));
        return data;
    }

    private List<Service> dataServices(){
        List<Service> data = new ArrayList<>();
        data.add(new Service(null, "Guarderia", "Protejemos y cuidamos tu mascota para que se sienta como en casa, sal sin preocuparte por quien la cuidara.", 49.999, "Dia", convertImagesToBlob("service_1")));
        data.add(new Service(null, "Veterinaria 24/7", "Un percance nunca esta previsto, su solucion claro que si, por eso contamos con los mejores veterinarios..", 69.999, "Consulta", convertImagesToBlob("service_2")));
        data.add(new Service(null, "Transporte Especial", "Viajar puede ser complicado con mascotas, descomplicate con nuestro servicio de transporte personalizado.", 5.399, "Km", convertImagesToBlob("service_3")));
        data.add(new Service(null, "Paseador", "Te levantas temprano, te acuesta tarde nunca hay tiempo para sacarlo afuera, no te preocupes un paseador lo hará.", 39.999, "Mes", convertImagesToBlob("service_4")));
        data.add(new Service(null, "Peluqueria", "Pelos por todos lados o una cuestion de estilo, que tu mascota siempre luzca de la mejor manera, cucheau!!.", 29.999, "null", convertImagesToBlob("service_5")));
        return data;
    }

    private List<Store> dataStores(){
        List<Store> data = new ArrayList<>();
        data.add(new Store(null, "Calle 46 # 30 - 25", "Te invitamos a visitarnos y vivir una experiencia magica junto a tu mascota, PetShop tu mejor solución.", "Bogotá DC", "Cundinamarca", 4.63527342, -74.07909264,convertImagesToBlob("store_1")));
        data.add(new Store(null, "Calle 33 # 44a - 09", "Te invitamos a visitarnos y vivir una experiencia magica junto a tu mascota, PetShop tu mejor solución.", "Medellin", "Antioquia", 6.23423239, -75.57207043,convertImagesToBlob("store_2")));
        data.add(new Store(null, "Carrera 26 # 56 - 89", "Te invitamos a visitarnos y vivir una experiencia magica junto a tu mascota, PetShop tu mejor solución.", "Cali", "Valle del Cauca", 3.43801944, -76.49564823,convertImagesToBlob("store_3")));
        data.add(new Store(null, "Calle 45 # 26 -05", "Te invitamos a visitarnos y vivir una experiencia magica junto a tu mascota, PetShop tu mejor solución.", "Barranquilla", "Atlantico", 10.96801629, -74.79074467,convertImagesToBlob("store_4")));
        data.add(new Store(null, "Tv 54 # 62 - 14", "Te invitamos a visitarnos y vivir una experiencia magica junto a tu mascota, PetShop tu mejor solución.", "Cartagena", "Bolivar", 10.38760627, -75.50860987,convertImagesToBlob("store_5")));
        return data;
    }

    // QUERIES
    private final String CREATE_PRODUCTS_TABLE = "CREATE TABLE products(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name VARCHAR," +
            "description TEXT," +
            "price REAL," +
            "image BLOB" +
            ");";

    private final String CREATE_SERVICES_TABLE = "CREATE TABLE services(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name VARCHAR," +
            "description TEXT," +
            "price REAL," +
            "mode VARCHAR," +
            "image BLOB" +
            ");";

    private final String CREATE_STORES_TABLE = "CREATE TABLE stores(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "address VARCHAR," +
            "description TEXT," +
            "city VARCHAR," +
            "state VARCHAR," +
            "latitude REAL," +
            "longitude REAL," +
            "image BLOB" +
            ");";

    private final String CREATE_FAVORITES_TABLE = "CREATE TABLE favorites(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "product_id INTEGER NOT NULL," +
            "user_id INTEGER NOT NULL," +
            "FOREIGN KEY (product_id) REFERENCES products (id)" +
            ");";

    private final String DELETE_PRODUCTS_TABLE = "DROP IF EXIST TABLE products";

    private final String DELETE_SERVICES_TABLE = "DROP IF EXIST TABLE services";

    private final String DELETE_STORES_TABLE = "DROP IF EXIST TABLE stores";

}
