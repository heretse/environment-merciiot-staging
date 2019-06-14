export SVC_NAME='mysqldb-merc';
export TAG_NAME=$(cat VERSION);

if [ "${TAG_NAME}" ]; then 
export IMAGE_NAME="merciiot/${SVC_NAME}:${TAG_NAME}";
echo "service name : ${SVC_NAME}";
echo "tag version : ${TAG_NAME}";
echo "image name : ${IMAGE_NAME}";
docker build -f ./Dockerfile -t "${IMAGE_NAME}" .;
else echo "Can not find version number from VERSION";
fi