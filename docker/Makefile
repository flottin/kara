# Add the following 'help' target to your Makefile
# And add help text after each target name starting with '\#\#'
 
help:			## Show this help.
	@fgrep -h "##" $(MAKEFILE_LIST) | fgrep -v fgrep | sed -e 's/\\$$//' | sed -e 's/##//'
 
# Everything below is an example
 
ansible-init:		## make docker image init
	ansible-playbook -i hosts playbook-docker-init.yml
 
ansible-install:	## install docker with ansible
	docker-compose up -d  && ansible-playbook -i hosts playbook.yml

docker-restart:		## exec restart
	docker-compose up -d && docker-compose exec mpe bash

docker-exec:		## exec docker
	docker-compose exec mpe bash

docker-ps-all:		## docker ps all
	docker images && docker ps -a

docker-ps:		## docker ps
	@docker images && docker ps -a	--format '\nCONTAINER LIST\n \
	container : {{.Names}} \n \
	status : {{.Status}} \n \
	ports : {{.Ports}}  \n'

docker-fix-image:	## docker fix container as mpe image
	docker commit mpe_container mpe_image:latest
