db.createUser(
  {
    user: "auth_admin",
    pwd: "gemtek123",
    roles: [
       { role: "readWrite", db: "authdb" }
    ]
  }
)
