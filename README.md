Unprotected endpoints

register new user
Request type: Post
URL: /register
Request body:
{
    "username": "username",
    "email": "email",
    "password": "password"
}

login to app
Request type: Post
URL: /login
Request body:
{
    "username": "username",
    "password": "password"
}

Protected endpoints (JWT token required in request header)

Show login users
Request type :Get
URL: users/active

Logout
Request type :Get
URL: users/logout

Show all chat rooms
Request type :Get
URL: rooms/

Create new chat room
Request type :Post
URL: rooms/
Request body:
{
    "name": "room name"
}

Delete chat room (only creator of the room)
Request type :Del
URL: rooms/1

Show chat room messages
Request type :Get
URL: rooms/1/messages

Create new message
Request type :Post
URL: /rooms/1/messages
Request body:
{
    "text": "message text"
}

Delete message (only creator of the message)
Request type :Del
URL: /rooms/messages/2

