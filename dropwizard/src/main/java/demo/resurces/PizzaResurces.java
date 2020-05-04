package demo.resurces;

import com.google.common.base.Optional;
import demo.api.Pizza;
import com.codahale.metrics.annotation.Timed;
import demo.services.PizzaService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/pizza")
@Produces(MediaType.APPLICATION_JSON)
public class PizzaResurces {

    private final String message;
    private final String user;

    public PizzaResurces(String message, String user) {
        this.message = message;
        this.user = user;
    }

    @GET
    @Timed
    public Pizza getPizza(@QueryParam("name") Optional<String> name
            , @QueryParam("user") Optional<String> httpUser) {
        final String value = String.format(message, httpUser.or(user), name.or("Pizza"));
        Pizza pizza = PizzaService.getPizza(name.or("Pizza"));

        if (pizza == null) {
            return new Pizza(value, 0.0, 0);
        }

        return pizza;
    }

    @POST
    @Timed
    @Path("/add")
    public Response addPizza(@QueryParam("name") String name,
                             @QueryParam("price") String price,
                             @QueryParam("count") String count) {

        if (PizzaService.getPizza(name) != null)
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("Pizza " + name +
                            " already exists").type("text/plain").build();

        double priceToUse;
        try {
            priceToUse = new Double(price);
        } catch (NumberFormatException e) {
            priceToUse = 0.0;
        }
        int countToUse;
        try {
            countToUse = new Integer(count);
        } catch (NumberFormatException e) {
            countToUse = 0;
        }
        PizzaService.addPizza(new Pizza(name, priceToUse,
                countToUse));

        return Response.status(Response.Status.OK).
                entity(PizzaService.getPizza(name)).type("application/json").build();
    }
}