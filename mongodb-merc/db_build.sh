#! /bin/bash
date=$(date '+%Y%m%d')
declare -a backup_folders=("argi")
for backup in "${backup_folders[@]}"
do
   echo "Bulid $backup"
   mongorestore -h 127.0.0.1 --port 27017 -u $MONGO_INITDB_ROOT_USERNAME -p $MONGO_INITDB_ROOT_PASSWORD --authenticationDatabase admin -d $backup /docker-entrypoint-initdb.d/$backup
done

echo "Add user to access db"
mongo argi -u $MONGO_INITDB_ROOT_USERNAME -p $MONGO_INITDB_ROOT_PASSWORD --authenticationDatabase admin /docker-entrypoint-initdb.d/addUser.js
