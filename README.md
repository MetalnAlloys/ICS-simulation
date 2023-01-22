# ICS-simulation
This repo contains the artefacts, namely configuration files and scripts that will assist in the project

## How to
### Requirements
The following software needs to be installed and running in order to reproduce the setup. Make note that this documentation is for the OS running Linux kernels.
- GNS3: refer to the installatioun documentation for your specific OS (https://docs.gns3.com/docs/getting-started/installation/linux/)
- QEMU for your target architecture, usually qemu-system-x86_64. Refer to your OS documentation
- libvirtd - Libvirt deamon for Virtual machines management
  - It will most probably be installed automatically when you install QEMU. Make sure it is started prior to use QEMU e.g. on systemd based Linux kernels use 
 ```bash
    systemctl start libvirtd.service
 ```
  - Otherwise, requires kernel KVM module to be laoded
    + Check your hardware for virtualization/KVM support e.g. use the command
 ```bash
 C_ALL=C lscpu | grep Virtualization
``` 
  - Alternatively, a program that I use is called virt-manager (https://virt-manager.org/), which is the best option to manage QEMU virtual machines from a desktop GUI.
- VNCviewer of any kind. I use tigervnc (https://tigervnc.org/)
- Docker runtime. Check installation instructions at https://docs.docker.com/get-docker/

### Repo directories key
- virtual_hardware
  + configurations and info for virtual switch and routers
- gns_appliances
  + contains .gns3a format files to import gns appliances private or found on the marketplace
- pcap_files
  + contains .pcap files during packet capture in GNS3
- services
  + contains configurations for services and VMs needed for gns
- main  
  + The main project file and other related things
- gns
  + contains anything needed for gns

## How to configure VyOS
After creating a VM from the VyOS image, it will require some configuration to work. VyOS looks for a config.boot file to load after it starts. The provided file can be put into the path `/opt/vyatta/etc/config/config.boot`. However, configuring by hand is better, therefore a config script is provided in the services/vyos directory. The script can simply executed on a fresh VyOS install

## Using docker images in GNS3
A docker container template can be created the same way as of creating VMs in GNS3. However, the networking part may requires some tweaking esp. in this topology, a docker container should get its IP address from DHCP server. Therefore, after creating the container, edit its networking configuration from GNS3 to be:

```bash
    auto eth0
    iface eth0 inet dhcp
    hostname my-docker-container
```
### How to run Scada-LTS docker image
- Navigate to services/scada-lts
- Check the file docker-compose.yml for information
- Run `dockerc compose up -d`

### How to run OpenPLC docker image
- Run `docker run -d --privileged -v $PWD/workdir:/workdir -p 8080:8080 -p 502:502 openplc:v3`
- Make sure you have a directory named workdir in you current directory
- This command will run a docker container with port 8080(for web GUI) and 502(for Modbus) exposed to the host system
- There will also be persistent storage for openPLC in order to survive restarts


## Setup topology
The current topology looks like below as of now. The topology follows the ISA-62443 standard model of zone and conduits

<img src="/screenshots/ics_mod4.png" alt="Alt text" >

## Sample process visualization

<img src="/screenshots/process_visual.jpg" alt="Alt text" width="512" 
     height="384" >
