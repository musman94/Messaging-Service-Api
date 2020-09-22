# Get Message History

Gets the message hitory of a user.

**URL** : `/api/v1/message/getMessageHistory?username={username}`

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
    "payload": [
        {
            "to": "musman1",
            "message": "adsasdfdcsd"
        },
        {
            "to": "musman2",
            "message": "adsasdfdcsd"
        }
    ]
}
```

## Error Responses

**Condition** : If the username is not found.

**Code** : `404 NOT FOUND`

**Content example**

```json
{
    "errors": {
        "errorMessage": "USER musma not found"
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

