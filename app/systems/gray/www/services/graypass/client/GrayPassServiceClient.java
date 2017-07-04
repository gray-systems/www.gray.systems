/*******************************************************************************
 * www.gray.systems - a Play application running at https://www.gray.systems
 * Copyright (C) 2017 (name: Jared Gray, email: jared@gray.systems)
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Affero General Public License as published by the Free Software Foundation, either version
 * 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package systems.gray.www.services.graypass.client;

import java.io.File;
import java.io.IOException;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.io.CharSink;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import systems.gray.www.services.common.exceptions.BadRequestException;
import systems.gray.www.services.common.exceptions.InternalServerException;

/**
 * A client to a GrayPassService.
 */
public class GrayPassServiceClient {

    private static final Gson gson = new Gson();

    /**
     * Create a new client.
     * 
     * @param config configures the client
     * @return a non-<code>null</code>, configured {@link GrayPassServiceClient}
     */
    public static GrayPassServiceClient create(GrayPassServiceClientConfig config) {
        return new GrayPassServiceClient(config);
    }

    /**
     * @return a non-<code>null</code> {@link KeyValueStore} for the input JSON-encoded file
     * @throws IOException when input file cannot be read
     * @throws JsonSyntaxException when input file syntax is incorrect
     */
    private static KeyValueStore extractJson(File file) throws IOException, JsonSyntaxException {
        String jsonIn = Files.asCharSource(file, Charsets.UTF_8).read();
        return gson.fromJson(jsonIn, KeyValueStore.class);
    }
    
    private final File vaultFile;

    private GrayPassServiceClient(GrayPassServiceClientConfig config) {
        // extract json from the vaultFile
        String vaultPath = Strings.nullToEmpty(config.getPathToVault());
        vaultFile = new File(vaultPath);
        try {
            extractJson(vaultFile);
        } catch (JsonSyntaxException e) {
            // couldn't parse the file
            throw new BadRequestException(e, "Invalid JSON");
        } catch (IOException e) {
            // couldn't read the file
            throw new InternalServerException(e);
        }
    }

    public void addUsernamePassword(String url, String username, String password, boolean confirm)
            throws BadRequestException {
        try {
            // construct updated key-value store
            KeyValueStore store = extractJson(vaultFile);
            String safeUrl = Strings.nullToEmpty(url);
            String safeUsername = Strings.nullToEmpty(username);
            store.put(safeUrl, safeUsername);
            String jsonOut = gson.toJson(store, KeyValueStore.class);
            
            // write file out
            CharSink sinkOut = Files.asCharSink(vaultFile, Charsets.UTF_8);
            sinkOut.write(jsonOut);
        } catch (JsonSyntaxException e) {
            // couldn't parse the file
            throw new BadRequestException(e, "Invalid JSON");
        } catch (IOException e) {
            // couldn't read the file
            throw new InternalServerException(e);
        }

    }
}
