db.createUser(
  {
    user: "admin",
    pwd: "gemtek123",
    roles: [
       { role: "readWrite", db: "argi_dev" }
    ]
  }
)