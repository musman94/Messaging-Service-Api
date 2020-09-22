# Get Login History

Gets the login hitory of a user.

**URL** : `/api/v1/user/getLoginHistory?username={username}`

**Method** : `GET`

**Auth required** : NO

**Permissions required** : YES

**Data constraints**

Provide the username in the url.   

## Success Responses

**Condition** : If the user exists.

**Code** : `200 OK`

**Content example**

```json
{
    "status": "OK",
    "payload": {
        "loginHistory": [
            {
                "Status": "AUTHENTICATION_SUCCESSFUL",
                "Date": "2020-09-22T11:21:36.329+00:00"
            },
            {
                "Status": "AUTHENTICATION_FAILED",
                "Date": "2020-09-22T11:27:29.978+00:00"
            }
        ]
    }
}
```

## Error Responses

**Condition** : If the username is not found.

**Code** : `404 NOT FOUND`

**Content example**

```json
{
    "errors": {
        "errorMessage": "USER musman not found"
    }
}
```

### Or

**Condition** : If the jwt token is missing.

**Code** : `404 NOT FOUND`

```json
{
    "errorMessage": "Full authentication is required to access this resource"
}
```

### Or

**Condition** : If of the any fields are missing.

**Code** : `400 BAD REQUEST`

