# Sends message to a user

**URL** : `/api/v1/message/sendMessage`

**Method** : `POST`

**Auth required** : NO

**Permissions required** : YES

**Data constraints**

usernames of the sender, reciever and the the message body.

```json
{
    "from":    "[string]",
    "to"  :    "[string]",
    "message": "[string]"
}
```

**Data example** All fields must be sent.

```json
{
    "from": "musman1",
    "to": "musman2",
    "message": "exampleMessage"
}
```

## Success Response

**Condition** : If everything is OK and both of the users exist.

**Code** : `200 OK`

**Content example**

```json
{
    "status": "OK",
    "message": "Message Sent"
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

**Condition** : If the sender was blocked by the reciever, a not found user error will be thrown where the the not found user is the reciever.

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

