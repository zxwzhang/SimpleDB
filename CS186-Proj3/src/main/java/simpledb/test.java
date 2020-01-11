package simpledb;
import java.io.*;

public class test {

    //！！！！！！记住换行符！！！！！！！！！！！
    public static void main(String[] argv){


        // construct a 3-column table schema
        Type types[] = new Type[]{ Type.INT_TYPE, Type.INT_TYPE};
        String names[] = new String[]{ "field0", "field1"};
        TupleDesc descriptor = new TupleDesc(types, names);
        
        // create the table, associate it with some_data_file.dat
        // and tell the catalog about the schema of this table.
        HeapFile table1 = new HeapFile(new File("CS186-Proj2/data.dat"), descriptor);
        Database.getCatalog().addTable(table1, "test");
        
        // construct the query: we use a simple SeqScan, which spoonfeeds
        // tuples via its iterator.
        TransactionId tid = new TransactionId();
        SeqScan f = new SeqScan(tid, table1.getId());
        
        try {
            // and run it
            f.open();
            while (f.hasNext()) {
                Tuple tup = f.next();
                System.out.println(tup);
            }
            f.close();
            Database.getBufferPool().transactionComplete(tid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}