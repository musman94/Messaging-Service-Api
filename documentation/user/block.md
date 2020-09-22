# Blocks User

Blocks the user.

**URL** : `/api/v1/user/block`

**Method** : `POST`

**Auth required** : NO

**Permissions required** : YES

**Data constraints**

Provide the usernames of the blocker and the to be blocked user.

```json
{
    "username": "[string]",
    "toBlockUsername": "[string]"
}
```

**Data example** All fields must be sent.

```json
{  
    "username": "musman1",
    "toBlockUsername": "musman2"
}
```

## Success Response

**Condition** : If everything is OK and both of the users exists.

**Code** : `200 OK`

**Content example**

```json
{
    "status": "OK",
    "message": "User blocked"
}
```

## Error Responses

**Condition** : If any of the users don't exist.

**Code** : `404 NOT FOUND`

```json
{
    "status": "NOT_FOUND",
    "errors": {
        "message": "USER musman not found"
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

**Condition** : If any of fields are missing.

**Code** : `400 BAD REQUEST`
