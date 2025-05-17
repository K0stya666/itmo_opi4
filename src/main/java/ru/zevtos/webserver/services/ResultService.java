package ru.zevtos.webserver.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import ru.zevtos.webserver.entities.Result;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class ResultService implements Serializable {

    @PersistenceContext(unitName = "StudsPU")
    private EntityManager em;
    
    /**
     * Сохраняет результат проверки попадания точки в область.
     *
     * @param result объект Result, который нужно сохранить.
     */
    @Transactional
    public void save(Result result) {
        em.persist(result);
    }

    /**
     * Извлекает список всех результатов из базы данных.
     *
     * @return список объектов Result.
     */
    @Transactional
    public List<Result> findAll() {
        return em.createQuery("SELECT r FROM Result r", Result.class).getResultList();
    }

    /**
     * Извлекает результат по его идентификатору.
     *
     * @param id идентификатор результата.
     * @return объект Result или null, если объект не найден.
     */
    @Transactional
    public Result findById(Long id) {
        return em.find(Result.class, id);
    }

    /**
     * Удаляет результат из базы данных по его идентификатору.
     *
     * @param id идентификатор результата, который нужно удалить.
     */
    @Transactional
    public void deleteById(Long id) {
        Result result = findById(id);
        if (result != null) {
            em.remove(result);
        }
    }

    /**
     * Удаляет все результаты из базы данных.
     */
    @Transactional
    public void deleteAll() {
        em.createQuery("DELETE FROM Result").executeUpdate();
    }


}
