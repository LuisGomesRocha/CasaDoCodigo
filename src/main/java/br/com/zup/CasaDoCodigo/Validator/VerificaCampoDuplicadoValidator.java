package br.com.zup.CasaDoCodigo.Validator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class VerificaCampoDuplicadoValidator
        implements ConstraintValidator<VerificaCampoDuplicado,Object> {

    private String campo;
    private Class<?> clazz;

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void initialize(VerificaCampoDuplicado parameters) {

        this.campo = parameters.attribute();
        this.clazz = parameters.clazz();
    }

    @Override
    public boolean isValid(Object objetoValidacao, ConstraintValidatorContext context) {

        Query query = manager.createQuery("SELECT 1 FROM "+ clazz.getName() + " where " + campo + " =:valor");
        query.setParameter("valor", objetoValidacao);

        var resultList = query.getResultList();

        return resultList.size() > 0 ? false: true;

    }


}
