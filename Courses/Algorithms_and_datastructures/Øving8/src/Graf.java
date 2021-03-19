import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;


public class Graf {
    int N, K;
    Node[] node;



    public void nyVektetGraf(BufferedReader br)throws IOException{
        StringTokenizer st=new StringTokenizer(br.readLine());
        N=Integer.parseInt(st.nextToken());
        node=new Node[N];

        for (int i = 0; i <N ; i++) {
            node[i]=new Node();
        }

        K=Integer.parseInt(st.nextToken());
        for (int i = 0; i <K ; i++) {
            st=new StringTokenizer(br.readLine());
            int fra=Integer.parseInt(st.nextToken());
            int til =Integer.parseInt(st.nextToken());
            int vekt=Integer.parseInt(st.nextToken());

            //Legg til sjekk om det er revers fra før av.
            Vkant a= new Vkant(node[til],(Vkant)node[fra].kant1,vekt);
            Vkant b= new Vkant(node[fra],(Vkant)node[til].kant1,0);

            a.setRevers(b);
            b.setRevers(a);

            node[fra].kant1= a;
            node[til].kant1= b;
        }
    }




    public void initforgj(Node s){
        for(int i= N; i-- >0;){
            node[i].d = new Forgj();
        }
        ((Forgj)s.d).dist =0;
    }


    public void bfs(Node s){
        initforgj(s);
        Kø kø = new Kø(N-1);
        kø.leggIKø(s);
        while(!kø.tom()){
            Node n = (Node) kø.nesteIKø();
            for(Kant k = n.kant1; k!= null; k=k.neste){
                Forgj f = (Forgj) k.til.d;
                if(f.dist == f.uendelig && ((Vkant)k).getRestKapasitet()>0){
                    f.dist = ((Forgj)n.d).dist +1;
                    f.forgj = n;
                    kø.leggIKø(k.til);
                }
            }
        }
    }

    public int finnFlytOkning(ArrayList<Integer> vei){
        int minsteRestKapasitet =100000;

        for(int i=1; i<vei.size(); i++){
            Node cur = node[vei.get((i))];

            for(Kant k = cur.kant1; k!= null; k=k.neste){
                if (k.til == node[vei.get(i-1)]){
                    if(((Vkant)k).getRestKapasitet()<minsteRestKapasitet){
                        minsteRestKapasitet = ((Vkant)k).getRestKapasitet();
                    }
                }
            }
        }
        if(minsteRestKapasitet !=  100000){
            return minsteRestKapasitet;
        } else{
            return 0;
        }

    }


    public int oppdaterKant(ArrayList<Integer> bfsVei){
        ArrayList<Integer> vei =  bfsVei;

        int flyt = finnFlytOkning(vei);
        System.out.print(flyt + " ");

        for(int i=1; i<vei.size(); i++){
            Node cur = node[vei.get((i))];

            for(Kant k = cur.kant1; k!= null; k=k.neste){
                if (k.til == node[vei.get(i-1)]){
                    ((Vkant)k).flyt += flyt;
                    ((Vkant)k.revers).flyt -= flyt;
                }
            }
        }
        return flyt;
    }



    public ArrayList<Integer> finnVeiTilbake(int start, int sluk){
        ArrayList<Integer>nodes = new ArrayList<>();
        Node current = node[sluk];
        Node stopp = node[start];

        while(current != null){
            for(int i=0; i<node.length; i++){
                if(current == node[i]){
                    nodes.add(i);
                }
            }
            current = ((Forgj)current.d).finn_forgj();
        }
        return nodes;
    }



}




