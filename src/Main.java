import java.io.*;
import java.util.*;

/**
 * Created by TA on 10-04-2017.
 */
public class Main {

    private static Scanner scanner;
    private static Map<Integer,List<String>> lR0Items = new HashMap<>();
    private static ArrayList<String> prodadd = new ArrayList<>();
    private static List<String> temppro = new ArrayList<>();


    private static Queue<String> queue = new LinkedList<>();


    private static int i,j,k,n;
    private static ArrayList<String> production =new ArrayList<String>();

    private static List<String> storePro = null;

    private static  Map<Integer, String>  rmap = new HashMap<>();
    private static Map<String,List<String>> map = new HashMap<String,List<String>>();

    private static Map<String,List<String>> checked = new HashMap<String,List<String>>();
    private static ArrayList<String> charList = new ArrayList<String>();



    public static void main(String[] args) throws IOException
    {
        //System.out.println("Hello");

        int noOfProductions = 1;

        LineNumberReader lnr = null;
        try {
            scanner = new Scanner(new File("input.txt"));
            lnr = new LineNumberReader(new FileReader(new File("input.txt")));
            lnr.skip(Long.MAX_VALUE);

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            n=0;
        }
        n=lnr.getLineNumber() + 1;//Add 1 because line index starts at 0
// Finally, the LineNumberReader object should be closed to prevent resource leak
        lnr.close();

        for(i=0;i<n;i++)
        {

            production.add(scanner.next());
            ArrayList<String> tempProduction = new ArrayList<String>();
            String current = production.get(i);
            String c= current.charAt(0)+"";
            charList.add("Z");
            charList.add(c);
            StringBuilder stringBuilder = new StringBuilder();



            //System.out.print(charList);

            j=3;
            if(i==0) {

                tempProduction.add(current.charAt(0)+"");
                map.put("Z",tempProduction);
                tempProduction = new ArrayList<>();
                rmap.put(0, "Z-" + current.charAt(0));
            }
            while(j<current.length())
            {
                if(current.charAt(j) == '|')
                {
                    rmap.put(noOfProductions++,current.charAt(0)+"-"+stringBuilder.toString());
                    tempProduction.add(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                }
                else
                {
                    stringBuilder.append(current.charAt(j));
                }
                j++;
            }
            tempProduction.add(stringBuilder.toString());
            map.put(c,tempProduction);

            rmap.put(noOfProductions++,current.charAt(0)+"-"+stringBuilder.toString());

        }


        System.out.println(rmap);
        System.out.println(map);




        storePro = map.get("Z");
        String st;
        st="Z-"+"."+storePro.get(0).toString();
        queue.add("Z-"+"."+storePro.get(0).toString());
        prodadd.add(st);
        subsequent(st);
        ArrayList<String> abc = new ArrayList<String>(prodadd);


        lR0Items.put(lR0Items.size(),abc);

        System.out.println("pro:"+temppro);
        System.out.println("LR" + lR0Items);


int count = 0;

        while (!queue.isEmpty()&&count<30)
        {
            count++;
            String curr = queue.remove();

           //prodadd.add(curr);
            System.out.println("Under Con:"+curr);
            System.out.println("LR0"+lR0Items);


            int i=0;
            while(curr.charAt(i)!='.')
            {
                i++;
            }
            if(curr.length()>i+1)
            {
                String te = curr.substring(0,i)+curr.charAt(i+1)+".";
                if(curr.length()!=i+2)
                    te+=curr.substring(i+2);

                curr=te;
                prodadd.clear();
                prodadd.add(curr);
                System.out.println("Update :"+curr);
                queue.add(curr);
                subsequent(curr);
                System.out.println("proad :"+prodadd);
            }
            else
            {
                continue;
            }


            /*if(curr.matches("^(.*?(\\.+[A-Z])[^$]*)"))
            {
                int i=0;
                while(curr.charAt(i)!='.')
                {
                    i++;
                }
                //System.out.println(i);
                //System.out.println(curr.charAt(i+1));
                if((curr.charAt(i+1)+"").matches("[A-Z]"))
                {

                    storePro = map.get(curr.charAt(i+1)+"");
                    for (int j = 0; j < storePro.size(); j++) {
                        queue.add(curr.charAt(i+1)+"-."+storePro.get(j).toString());
                        prodadd.add(curr.charAt(i+1)+"-."+storePro.get(j).toString());
                    }
                }
            }
            else {


            }*/


            boolean flag = false;

            for (int j = 0; j <lR0Items.size() ; j++) {

                temppro = lR0Items.get(j);

                if(temppro.equals(prodadd))
                {
                    flag=true;
                }

            }
            if(flag==false)
            {
                ArrayList<String> xyz = new ArrayList<String>(prodadd);

                lR0Items.put(lR0Items.size(),xyz);
            }

            prodadd.clear();


            /*if(queue.size()>100)
                queue.clear();*/

        }


        parsingTable();



    }

    public static void parsingTable()
    {

        for (int l = 0; l <lR0Items.size() ; l++) {

            temppro= lR0Items.get(l);

            

        }

    }

    public static void subsequent(String st) {
        int i=0;
        while(st.charAt(i)!='.')
        {
            i++;
        }
        if(st.length()==i+1)
        {
            return;
        }
        if((st.charAt(i+1)+"").matches("[A-Z]"))
        {
            storePro = map.get(st.charAt(i+1)+"");
            for (int j = 0; j < storePro.size(); j++) {
                queue.add(st.charAt(i+1)+"-."+storePro.get(j).toString());
                prodadd.add(st.charAt(i+1)+"-."+storePro.get(j).toString());
                if((storePro.get(j).charAt(0)+"").matches("[A-Z]"))
                {
                    subsequent(st.charAt(i+1)+"-."+storePro.get(j).toString());
                }
            }
        }

    }

}
