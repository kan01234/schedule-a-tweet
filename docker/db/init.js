db = db.getSiblingDB('tweet-dev');
db.createUser({
    user: "user",
    pwd: "pass",
    roles: [
        {
            role: "readWrite",
            db: "tweet-dev"
        }
    ]
  });
