# ICS-simulation
Code repo for ICS/SCADA virtualization thesis 

## How to
### Requirements
The following software needs to be installed and running in order to reproduc. Make note that this documentation is for the OS running Linux kernels.
- GNS3: refer to the installatioun documentation for your specific OS (https://docs.gns3.com/docs/getting-started/installation/linux/)
- QEMU for your target architecture, usually qemu-system-x86_64. Refer to your OS documentation
- libvirtd - Libvirt deamon for Virtual machines management
  - It will most probably be installed automatically when you install QEMU. Make sure it is started prior to use QEMU e.g. on systemd based Linux kernels use 
 ```bash
    systemctl start libvirtd.service
 ```
  - Alternatively, install a program called virt-manager (https://virt-manager.org/), which is the best option to manage QEMU virtual machines from a desktop GUI.

- VNC viewer of any kind. I use tigervnc (https://tigervnc.org/) (Optional, as
    all VMs inside GNS3 can also be managed by _tty_)
- Docker runtime and (Optional) docker compose. Check installation instructions at https://docs.docker.com/get-docker/

### Directories Key
- __gns_appliances__
  + contains .gns3a files to import gns appliances private or found on the GNS3 marketplace
- __pcap_files__
  + contains .pcap files during packet capture in GNS3
- __services__
  + contains configurations for services and VMs needed for GNS3
- __main__  
  + The main project code with python script for automated deployment

## Virtualization Setup
- The screenshot below is the target setup for this project 
- The network topology follows the ISA-62443 standard model of zone and conduits

<img src="/screenshots/ics_mod4.png" alt="Alt text" >


## Sample process visualization
The following figure shows the sample process that will run inside OpenPLC 

<img src="/screenshots/process_visual.jpg" alt="Alt text" width="512" 
     height="384" >


## How to deploy (Quick setup)
1. Make sure all the services i.e. GNS3, docker, and vnc viewer are running
2. Download the QEMU images (.qcow2 files) from the OneDrive link. Move all
   those images to your main GNS3 folder. e.g. /home/user/GNS3/images/QEMU/
3. Clone this repo and create a python virtual environment (optional)
```sh
    python3 -m venv <my-env-name>
    source env/bin/activate

    # Install dependencies
    cd ICS-simulation/main
    pip install -r requirements.txt

    # Run the script to deploy to GNS3
    python3 main.py <project-name>
    
```
4. The script will create a project using the name given above, install
   templates, create nodes and links

5. Go to the next section to configure individual VMs/nodes inside GNS3


## Services Overview
### VyOS
VyOS is used as primary router in this project

A VyOS VM in gns3 can be created as a gns3 template if you did not follow the quick setup or want more control. Follow the instruction on https://docs.vyos.io/en/equuleus/installation/virtual/gns3.html#requirements.

After creating a VM from the VyOS image, it will require some configuration to work. VyOS looks for a config.boot file to load after it starts. 
A config script is provided in the services/vyos directory. The script can simply executed on a fresh VyOS install and it will configure everything for this project. 

Note: You will have to copy the script inside VyOS. Therefore, open console to
VyOS inside GNS3 and run the following commands:

```sh
configure
set service ssh port '22'
exit

# Find vyos IP address
ip a

# Now you can use SSH from you host machine OR use scp copy the script
scp /path/to/script vyos@<IP>:/home/vyos

```


### Scada-LTS
Scada-LTS is an open-source implementation of a SCADA server (https://github.com/SCADA-LTS/Scada-LTS)

__How to run?__
1. Create a blank Linux VM ( a minimal linux OS like Alpine linux would be sufficient). GNS3 has apline linux appliance available which you can import directly into your project an install the necessary packages such as docker runtime. The appliance is found at https://gns3.com/marketplace/appliances/open-vswitch. Refer to https://docs.gns3.com/docs/using-gns3/beginners/import-gns3-appliance/ on how to import GNS3 appliances
2. Navigate to services/scada-lts
3. Check the file docker-compose.yml for information
4. Run `docker compose up -d`

### How to run OpenPLC docker image
- Run `docker run -d --privileged -v $PWD/workdir:/workdir -p 8080:8080 -p 502:502 openplc:v3`
- Make sure you have a directory named workdir in you current directory
- This command will run a docker container with port 8080(for web GUI) and 502(for Modbus) exposed to the host system
- There will also be persistent storage for openPLC in order to survive restarts



