zsh:1: command not found: lswq

from gns3fy import Gns3Connector, Project, Node, Link
from tabulate import tabulate
from pprint import pprint

import os, sys, shutil

QEMU_BINARY = shutil.which("qemu_x86_64")
GNS3_SERVER = "http://127.0.0.1:3080"
TEMPLATES = ['vyos', 'openplc', 'scada', 'openvswitch']
IMAGE_NAMES = ['vyso.img', 'scada.img']
TEMPLATE_TYPE="qemu"

server = Gns3Connector(url=GNS3_SERVER)

def print_summary():
    print(
        tabulate(
            server.projects_summary(is_print=False),
            headers=["Project Name", "Project ID", "Total Nodes", "Total Links", "Status"],
        )
    )

def create_templates(name, image):
    server.create_template(
        name=name,
        template_type="qemu",
        ram=512,
        hda_disk_image=image,
        hda_disk_interface="virtio",
        console_type="vnc",
        adapters=2,
        adapter_type="virtio-net-pci"
    )

def create_nodes(project_id):
    nodes = ["workstation", "router", "switch"]

    for n in nodes:
        switch = Node()
        switch.create()


def link_nodes(project_id):
    pass

if __name__ == "__main__":
    # if (argc := len(sys.argv)) < 2:
    #     print(f"Need project name for GNS3!")
    #     raise SystemExit(1)
    # else:
    #     project_name = sys.argv[1]

    lab = Project(name="api-test", connector=server)

    lab.get()
    lab.open()

    print(f"{lab.name}: {lab.project_id} -- Status {lab.status}")
    print(lab.stats)
    #print_summary()

    for template in server.get_templates():
        if "switch" in template["name"]:
            print(f"Template: {template['name']} -- ID: {template['template_id']}")

    switch = Node(
        project_id=lab.project_id,
        connector=server,
        name="Simple OS",
        template="Firefox"
    )

    switch.create()

    print(switch)

    print(lab.nodes)



















