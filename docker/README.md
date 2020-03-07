# building a docker image from an ansible deployed container

## generate key pair
```
mkdir -p .ssh
yes y | ssh-keygen -t rsa -b 4096  -f .ssh/id_rsa -N '' > /dev/null
```

## build image
```
docker build -t mpe_image .
```

## launch container as web server

```
# with docker 
docker run -d -P -p 22:22 -p 80:80 -p 443:443  -p 3306:3306 -h mpe --name mpe  \
--mount type=bind,source="$(pwd)"/../mpe,target=/data/apache2/mpe/htdocs \
--cap-add SYS_ADMIN -v /sys/fs/cgroup:/sys/fs/cgroup:ro mpe_image 
```

## enter container
```
# verify ssh connection
ssh -i .ssh/id_rsa root@localhost -p 2281
```

## ssl local generate
```
bin/mkcert -install
bin/mkcert localhost
mv localhost*.pem etc/ssl/certs/
```

## launch ansible playbook
```
ansible-playbook -i hosts playbook.yml 
```

## persist the container 
```
# when done export container as image
docker export mpe | gzip > mpe.tar.gz

# delete image and container
docker rm --force mpe
docker image rm mpe_image

# import container as image
zcat < mpe.tar.gz | docker import - mpe_image

# push the image in registry
docker tag mpe_imapge:latest localhost:5000/mpe_image:latest
docker push localhost:5000/mpe_image:latest

# delete image and container
docker image rm localhost:5000/mpe_image:latest

# pull the image
docker pull localhost:5000/mpe_image:latest
```

## enter the container
```
docker-compose up -d
```
