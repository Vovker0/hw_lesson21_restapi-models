package in.reqres;

import in.reqres.models.*;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static in.reqres.TestData.*;
import static in.reqres.specs.ResourceSpec.*;
import static in.reqres.specs.UserSpec.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiTests extends TestBase {

    @Test
    @Tag("API")
    @Owner("Vladimir")
    @DisplayName("Get resources list Test")
    public void getResourcesListTest() {

        ResourcesListModel response = step("Make request to get resources list", () ->
                given(resourcesRequestSpec)
                        .when()
                        .get(RES_LIST_URL)
                        .then()
                        .spec(resourcesFoundResponseSpec)
                        .extract().as(ResourcesListModel.class));

        step("Check that data has " + ITEMS + " items", () ->
                assertThat(response.getTotal()).isEqualTo(ITEMS));
        step("Check that data has " + TOTAL_PAGES + " total pages", () ->
                assertThat(response.getTotal_pages()).isEqualTo(TOTAL_PAGES));
        step("Check that data has " + ITEMS / TOTAL_PAGES + " items per page", () ->
                assertThat(response.getData()).hasSize(ITEMS / TOTAL_PAGES));
        step("Check that support URL is " + SUPPORT_URL, () ->
                assertThat(response.getSupport().getUrl()).isEqualTo(SUPPORT_URL));
    }

    @Test
    @Tag("API")
    @Owner("Vladimir")
    @DisplayName("Check resource item with particular ID from resources list")
    public void resourcesListIdDataTest() {

        ResourcesListModel response = step("Make request to get resources list", () ->
                given(resourcesRequestSpec)
                        .when()
                        .get(RES_LIST_URL)
                        .then()
                        .spec(resourcesFoundResponseSpec)
                        .extract().as(ResourcesListModel.class));

        step("Check that received data id is: " + DATA_ID, () ->
                assertThat(response.getData()[DATA_ID - 1].getId()).isEqualTo(4));
        step("Check that id " + DATA_ID + " name is " + DATA_ID_NAME, () ->
                assertThat(response.getData()[DATA_ID - 1].getName()).isEqualTo(DATA_ID_NAME));
        step("Check that received data id " + DATA_ID + " year is " + DATA_ID_YEAR, () ->
                assertThat(response.getData()[DATA_ID - 1].getYear()).isEqualTo(DATA_ID_YEAR));
        step("Check that id " + DATA_ID + " color is " + DATA_ID_COLOR, () ->
                assertThat(response.getData()[DATA_ID - 1].getColor()).isEqualTo(DATA_ID_COLOR));
        step("Check that id " + DATA_ID + " pantone is " + DATA_ID_PANTONE, () ->
                assertThat(response.getData()[DATA_ID - 1].getPantone_value()).isEqualTo(DATA_ID_PANTONE));
    }

    @Test
    @Tag("API")
    @Owner("Vladimir")
    @DisplayName("Unsuccessful try to get resource item with non-existent ID")
    public void unsuccessfulGetResourceTest() {
        SingleResourceModel response = step("Make request to get non-existent item id " + NON_EXISTENT_ID, () ->
                given(resourcesRequestSpec)
                        .when()
                        .get(RES_LIST_URL + "/" + NON_EXISTENT_ID)
                        .then()
                        .spec(resourceNotFoundSpec)
                        .extract().as(SingleResourceModel.class));

        step("Check that all item fields are null", () -> assertThat(response).hasAllNullFieldsOrProperties());
    }

    @Test
    @Tag("API")
    @Owner("Vladimir")
    @DisplayName("Create user Test")
    public void createUserTest() {
        CreateUpdateUserModel userData = new CreateUpdateUserModel();
        userData.setName(USER_NAME);
        userData.setJob(USER_JOB);

        CreateUserResponseModel response = step("Make request to create user" + USER_NAME, () ->
                given(createUpdateUserSpec)
                        .body(userData)
                        .when()
                        .post(USERS_URL)
                        .then()
                        .spec(createUserResponseSpec)
                        .extract().as(CreateUserResponseModel.class));

        step("Check that created user name is" + USER_NAME, () ->
                assertEquals(USER_NAME, response.getName()));
        step("Check that created user job is" + USER_JOB, () ->
                assertEquals(USER_JOB, response.getJob()));
    }

    @Test
    @Tag("API")
    @Owner("Vladimir")
    @DisplayName("Update user's job Test")
    public void updateUserTest() {
        CreateUpdateUserModel userData = new CreateUpdateUserModel();
        userData.setName(USER_NAME);
        userData.setJob(USER_NEW_JOB);

        UpdateUserResponseModel response = step("Make request to update user's job for the user " + USER_NAME,
                () -> given(createUpdateUserSpec)
                        .body(userData)
                        .when()
                        .patch(USERS_UPDATE_URL)
                        .then()
                        .spec(updateUserResponseSpec)
                        .extract().as(UpdateUserResponseModel.class));

        step("Check that updated user name is" + USER_NAME, () ->
                assertEquals(USER_NAME, response.getName()));
        step("Check that updated user job is" + USER_NEW_JOB, () ->
                assertEquals(USER_NEW_JOB, response.getJob()));
    }
}