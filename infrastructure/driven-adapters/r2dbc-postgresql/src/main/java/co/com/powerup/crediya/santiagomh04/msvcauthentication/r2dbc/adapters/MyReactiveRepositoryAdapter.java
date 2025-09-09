package co.com.powerup.crediya.santiagomh04.msvcauthentication.r2dbc.adapters;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.r2dbc.helper.ReactiveAdapterOperations;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.r2dbc.repositories.MyReactiveRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MyReactiveRepositoryAdapter extends ReactiveAdapterOperations<
    Object/* change for domain model */,
    Object/* change for adapter model */,
    String,
        MyReactiveRepository
> {
    public MyReactiveRepositoryAdapter(MyReactiveRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, Object.class/* change for domain model */));
    }

}
