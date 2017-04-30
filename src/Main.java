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
    private static List<String> temp = new ArrayList<>();

    private static HashMap<String, Integer> tab = new HashMap<String, Integer>();
    private static HashMap< Integer,String> rtap = new HashMap<Integer, String>();


    private static Queue<String> queue = new LinkedList<>();


    private static int i,j,n;
    private static ArrayList<String> production =new ArrayList<String>();

    private static List<String> storePro = null;

    private static  Map<Integer, String>  rmap = new HashMap<>();
    private static Map<String,List<String>> map = new HashMap<String,List<String>>();

    private static Map<String,List<String>> checked = new HashMap<String,List<String>>();
    private static ArrayList<String> charList = new ArrayList<String>();

    private static Set<String> nonte = new HashSet<>();
    private static Set<String> term = new HashSet<>();

    private static String[][] sLRParsingTab = new String[30][20];



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
            nonte.add("Z");



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
            nonte.add(c);
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


        findnonter();

        parsingTable();

        System.out.println("Enter the string you want to parse");
        Scanner scanner = new Scanner(System.in);
        String inp = scanner.nextLine();

        System.out.println(inp);

        parsingString(inp);


    }

    private static void parsingString(String inp) {

        inp+="$";

        Stack<String> stack = new Stack<>();

        stack.push("0");

        for (int k = 0; k <inp.length() ; k++) {

            String ex = inp.charAt(k)+"";

            String sc = stack.pop();
            stack.push(sc);

            String val = sLRParsingTab[Integer.parseInt(sc)][tab.get(ex)];

            System.out.println(k +" :"+val);

            if(val==null)
            {
                System.err.println("String Rejected");
                break;
            }
            if((val.charAt(0)+"").equals("S"))
            {
                stack.push(inp.charAt(k)+"");
                stack.push(val.charAt(1)+"");
            }
            else
            {
                String red = rmap.get(Integer.parseInt(val.charAt(1)+""));
                System.out.println(red + "  "+ stack);
                for (int l = 0; l < (red.length()-2)*2 ; l++) {
                    stack.pop();
                }
                String num = stack.pop();
                stack.push(num);
                stack.push(red.charAt(0)+"");
                stack.push(sLRParsingTab[Integer.parseInt(num)][tab.get(red.charAt(0)+"")]);

                k--;


            }
            System.out.println("Stack"+stack);

        }


    }

    private static void findnonter() {

        System.out.println("Non Terminals : "+nonte);


        for (String s : nonte) {

            List<String> temp = new ArrayList<>();

            temp = map.get(s);

            for(String t : temp)
            {
                //System.out.println(t);

                for (int l = 0; l <t.length() ; l++) {
                    if(!(t.charAt(l)+"").matches("[A-Z]"))
                    {
                        term.add(t.charAt(l)+"");
                    }
                }

            }
            //System.out.println(s);

        }


        term.add("$");
        System.out.println("Terminals "+term);


        int k =0;
        for(String s:term)
        {
            tab.put(s,k);
            rtap.put(k,s);
            k++;
        }
        for(String s:nonte)
        {
            tab.put(s,k);
            rtap.put(k,s);
            k++;
        }
        System.out.println("Rows "+tab);



    }

    public static void parsingTable()
    {


        DataFile.charList = charList;
        DataFile.map = map;
        DataFile.initialiseTerminals();
        FirstAndFollowSet Fs = new FirstAndFollowSet(map,charList);
        Fs.firstAndFollowSet();
        Map<String, ArrayList<String>> first = new HashMap<>();                                 // First Set
        Map<String, ArrayList<String>> follow = new HashMap<>();                                // Follow Set
        first = Fs.getFirst();
        follow = Fs.getFollow();

        System.out.println("Follow "+follow);


        System.out.println("\nParsing Table \n");



        System.out.print("Set\t");
        for (int k = 0; k <rtap.size() ; k++) {
            System.out.print(rtap.get(k)+" \t");
        }
        System.out.print("\n");


        for (int l = 0; l <lR0Items.size() ; l++) {

            temppro= lR0Items.get(l);

            int col =0;

            for(String z : temppro)
            {
                int k;
                int flag = 0;
                for (k = 0; k <rmap.size() ; k++) {
                    if(z.contains(rmap.get(k)))
                    {
                        flag=1;
                        break;
                    }
                }

                if(flag==1)
                {
                    for(String n: follow.get(rmap.get(k).charAt(0)+""))
                    {
                        sLRParsingTab[l][tab.get(n)]="R"+k;
                    }
                }
                else
                {
                    int m;
                    for ( m = 0; m <z.length() ; m++) {
                        if(z.charAt(m)=='.')
                        {
                            //System.out.println("M"+z);
                            //System.out.println("Check "+ z.charAt(m+1)+ "   "+tab.get(z.charAt(m+1)));
                            if((z.charAt(m+1)+"").matches("[A-Z]"))
                                sLRParsingTab[l][tab.get(z.charAt(m+1)+"")]=""+checkItemSets(z);
                            else
                                sLRParsingTab[l][tab.get(z.charAt(m+1)+"")]="S"+checkItemSets(z);
                            break;
                        }
                    }

                }

                col++;
            }
        }

        for (int k = 0; k < lR0Items.size(); k++) {
            System.out.print("I"+k+" \t");
            for (int l = 0; l <term.size()+nonte.size() ; l++) {
                if(sLRParsingTab[k][l]!=null)
                    System.out.print(sLRParsingTab[k][l]+"\t");
                else
                    System.out.print(" \t");
            }
            System.out.println(" ");
        }

    }

    private static int checkItemSets(String st)
    {
        int k=0;

        temp.clear();

        int i=0;
        while(st.charAt(i)!='.')
        {
            i++;
        }
        String te = st.substring(0,i)+st.charAt(i+1)+"."+st.substring(i+2);
        temp.add(te);
        checkSubsequent(te);

        for (int j = 0; j <lR0Items.size() ; j++) {
            if(temp.equals(lR0Items.get(j)))
                return j;
        }

        //System.out.println(temp);

        return 0;
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

    private static void checkSubsequent(String st)
    {
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
                temp.add(st.charAt(i+1)+"-."+storePro.get(j).toString());
                if((storePro.get(j).charAt(0)+"").matches("[A-Z]"))
                {
                    checkSubsequent(st.charAt(i+1)+"-."+storePro.get(j).toString());
                }
            }
        }
    }


}
