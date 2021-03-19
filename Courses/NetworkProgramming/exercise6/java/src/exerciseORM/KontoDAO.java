package exerciseORM;

import javax.persistence.*;
import java.util.List;


public class KontoDAO {
    private EntityManagerFactory emf;

    public KontoDAO(EntityManagerFactory emf){
        this.emf = emf;
    }


    public void lagNyKonto(Konto konto){
        EntityManager em = getEM();

        try{
            em.getTransaction().begin();
            em.persist(konto);
            em.getTransaction().commit();

        } catch (Exception e){
            System.out.println(e);
        } finally {
            lukkEM(em);
        }
    }

    public List<Konto> getAlleKontoerOverBelop(int belop){
        EntityManager em = getEM();
        try{
            Query q = em.createQuery("SELECT OBJECT(o) FROM Konto o where o.saldo > " + belop);
            //SELECT o FROM BOK o gir samme resultat
            //MERK at Bok m� ha stor B (samme som klassenavn)
            return q.getResultList();
        }finally{
            lukkEM(em);
        }
    }

    public void endreKonto(Konto konto){
        EntityManager em = getEM();
        try{
            em.getTransaction().begin();
            Konto endring = em.merge(konto);//s�rger for � f�re entiteten inn i lagringskonteksten
            em.getTransaction().commit();//merk at endringene gjort utenfor transaksjonen blir lagret!!!
        }finally{
            lukkEM(em);
        }
    }


    private EntityManager getEM(){
        return emf.createEntityManager();
    }

    private void lukkEM(EntityManager em){
        if (em != null && em.isOpen()) em.close();
    }


}
