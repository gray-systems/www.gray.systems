/*******************************************************************************
 * www.gray.systems - a Play application running at https://www.gray.systems Copyright (C) 2017
 * (name: Jared Gray, email: jared@gray.systems)
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Affero General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package systems.gray.www.controllers;

import javax.inject.Inject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Html;
import systems.gray.www.forms.User;
import systems.gray.www.services.auth.client.AuthServiceClient;
import systems.gray.www.services.auth.client.AuthServiceClientConfig;
import systems.gray.www.services.common.exceptions.BadRequestException;

/**
 * This controller contains an action to handle HTTP requests to the application's home page.
 */
public class HomeController extends Controller {

    private static final AuthServiceClient client;

    static {
        AuthServiceClientConfig config = new AuthServiceClientConfig();
        client = AuthServiceClient.create(config);
    }

    /** Helper to format Html */
    public static Html prettify(Html content) {
        Document doc = Jsoup.parse(content.body());
        return new Html(doc.html());
    }

    @Inject
    FormFactory formFactory;

    public Result authenticate() {
        Form<User> userForm = formFactory.form(User.class);
        try {
            userForm = userForm.bindFromRequest();
            if (!userForm.hasErrors()) {
                User user = userForm.get();
                client.authenticate(user.getUsername(), user.getPassword());
            } else {
                throw new BadRequestException(null, "Form had errors");
            }
            return null;
        } catch (BadRequestException e) {
            return badRequest(systems.gray.www.views.html.login.render(userForm));
        }
    }

    public Result index() {
        Html html = systems.gray.www.views.html.index.render();
        return ok(prettify(html));
    }

    public Result loginForm() {
        Form<User> userForm = formFactory.form(User.class);
        Html html = systems.gray.www.views.html.login.render(userForm);
        return ok(prettify(html));
    }
}
