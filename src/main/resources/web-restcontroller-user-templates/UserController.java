package ${topLevelDomain}.${companyName}.${productName}.restcontroller.user;

import ${topLevelDomain}.${companyName}.${productName}.model.JsonResponseData;
import ${topLevelDomain}.${companyName}.${productName}.model.criteria.UserSearchCriteriaData;
import ${topLevelDomain}.${companyName}.${productName}.model.data.UserProfile;
import ${topLevelDomain}.${companyName}.${productName}.model.service.user.UserService;
import ${topLevelDomain}.${companyName}.${productName}.restcontroller.ControllerHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * The REST entry point for the user services.
 *
 * @author ${codeAuthor}.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource(name = "UserService")
    private UserService userService;

    /**
     * The entry point to retrieve a user profile.
     *
     * @param uuid identifies the user.
     * @return the JSON response.
     */
    @Secured(value = "USER")
    @RequestMapping(value = "/retrieve", method = RequestMethod.GET, produces = { ControllerHelper.APPLICATION_JSON })
    public ResponseEntity<JsonResponseData> retrieve(@RequestParam String uuid) {
        final UserProfile userProfile = userService.retrieve(uuid);
        return ControllerHelper.createSuccessResponse(userProfile);
    }

    /**
     * The entry point to add a user to the application.
     *
     * @param entry the data.
     * @return the saved data.
     */
    @Secured(value = "ADMIN")
    @RequestMapping(value = "/profile/add", method = RequestMethod.POST, produces = {
            ControllerHelper.APPLICATION_JSON })
    public ResponseEntity<JsonResponseData> add(@RequestBody UserProfile entry) {
        userService.add(entry);

        // return the saved entry
        final UserProfile savedEntry = userService.retrieve(entry.getUuid());
        return ControllerHelper.createSuccessResponse(savedEntry);
    }

    /**
     * The entry point to add a user to the application.
     *
     * @param entry the data.
     * @return the saved data.
     */
    @Secured(value = "USER")
    @RequestMapping(value = "/profile/save", method = RequestMethod.POST, produces = {
            ControllerHelper.APPLICATION_JSON })
    public ResponseEntity<JsonResponseData> save(@RequestBody UserProfile entry) {
        userService.save(entry);

        // return the saved entry
        final UserProfile savedEntry = userService.retrieve(entry.getUuid());
        return ControllerHelper.createSuccessResponse(savedEntry);
    }

    /**
     * The entry point to add a user to the application.
     *
     * @param entry the data.
     * @return the saved data.
     */
    @Secured(value = "ADMIN")
    @RequestMapping(value = "/profile/delete", method = RequestMethod.POST, produces = {
            ControllerHelper.APPLICATION_JSON })
    public ResponseEntity<JsonResponseData> delete(@RequestBody UserProfile entry) {
        userService.delete(entry);

        // return the saved entry
        return ControllerHelper.createSuccessResponse();
    }

    /**
     * The entry point to search for users.
     *
     * @param criteria the search criteria.
     * @return the JSON response.
     */
    @Secured(value = "USER")
    @RequestMapping(value = "/search", method = RequestMethod.POST, produces = {
            "application/json; charset=UTF-8" })
    public ResponseEntity<JsonResponseData> search(@RequestBody UserSearchCriteriaData criteria) {
        final List<UserProfile> profiles = userService.search(criteria);
        return ControllerHelper.createListResponse(profiles);
    }

    /**
     * The entry point to add a user to the application.
     *
     * @param emailAddress    the users email address.
     * @param newPassword     the new password.
     * @param currentPassword the existing password.
     * @return the saved data.
     */
    @Secured(value = "USER")
    @RequestMapping(value = "/password", method = RequestMethod.POST, produces = {
            ControllerHelper.APPLICATION_JSON })
    public ResponseEntity<JsonResponseData> changePassword(@RequestParam String emailAddress, @RequestParam String
            newPassword, @RequestParam String currentPassword) {
        userService.changePassword(emailAddress, newPassword, currentPassword);

        // return the saved entry
        return ControllerHelper.createSuccessResponse();
    }

    /**
     * Sends a reset password confirmation email to the supplied address.
     *
     * @param emailAddress the users email address to send the reset email to.
     * @return the saved data.
     */
    @Secured(value = "USER")
    @RequestMapping(value = "/reset-confirmation", method = RequestMethod.POST, produces = {
            ControllerHelper.APPLICATION_JSON })
    public ResponseEntity<JsonResponseData> sendResetConfirmation(@RequestParam String emailAddress) {
        userService.sendResetConfirmation(emailAddress);

        // return the saved entry
        return ControllerHelper.createSuccessResponse();
    }

    /**
     * Resets the supplied users password that was initiated by a confirmation email.
     *
     * @param emailAddress the users email address to send the reset email to.
     * @param resetUuid    identifies the confirmation request.
     * @return the saved data.
     */
    @Secured(value = "USER")
    @RequestMapping(value = "/reset-password-by-confirmation", method = RequestMethod.POST, produces = {
            ControllerHelper.APPLICATION_JSON })
    public ResponseEntity<JsonResponseData> resetPasswordByConfirmation(@RequestParam String emailAddress,
            @RequestParam String resetUuid) {
        userService.resetPassword(emailAddress, resetUuid);

        // return the saved entry
        return ControllerHelper.createSuccessResponse();
    }

    /**
     * Resets the supplied users password.
     *
     * @param emailAddress the users email address to send the reset email to.
     * @return the saved data.
     */
    @Secured(value = "USER")
    @RequestMapping(value = "/reset-password", method = RequestMethod.POST, produces = {
            ControllerHelper.APPLICATION_JSON })
    public ResponseEntity<JsonResponseData> resetPassword(@RequestParam String emailAddress) {
        userService.resetPassword(emailAddress);

        // return the saved entry
        return ControllerHelper.createSuccessResponse();
    }
}
