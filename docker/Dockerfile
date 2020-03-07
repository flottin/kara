FROM ubuntu:19.10

ENV container docker
ENV LC_ALL C
ENV DEBIAN_FRONTEND noninteractive

RUN sed -i 's/# deb/deb/g' /etc/apt/sources.list

RUN ln -fs /usr/share/zoneinfo/Europe/Paris /etc/localtime
# install sytemd + python
RUN apt-get update \
    && apt-get install -y systemd \
    && apt-get install -y vim python \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

RUN cd /lib/systemd/system/sysinit.target.wants/ \
    && ls | grep -v systemd-tmpfiles-setup | xargs rm -f $1

RUN rm -f /lib/systemd/system/multi-user.target.wants/* \
    /etc/systemd/system/*.wants/* \
    /lib/systemd/system/local-fs.target.wants/* \
    /lib/systemd/system/sockets.target.wants/*udev* \
    /lib/systemd/system/sockets.target.wants/*initctl* \
    /lib/systemd/system/basic.target.wants/* \
    /lib/systemd/system/anaconda.target.wants/* \
    /lib/systemd/system/plymouth* \
    /lib/systemd/system/systemd-update-utmp*

COPY root/.bashrc /root/


EXPOSE 80

VOLUME [ "/sys/fs/cgroup", "/tmp", "/run", "/run/lock" ]
CMD ["/lib/systemd/systemd"]
