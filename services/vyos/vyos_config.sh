#!/bin/vbash

source /opt/vyatta/etc/functions/script-template
configure

# Commands to setup router interfaces
set interfaces ethernet eth0 address dhcp
set interfaces ethernet eth0 description 'external'
set interfaces ethernet eth1 address '192.168.0.1/24'
set interfaces ethernet eth1 description 'internal'

# Enable ssh. Optionally specify custome port by appending : port <port>
set service ssh


# Setup DHCP
# Set default gateway and subnet
set service dhcp-server shared-network-name LAN subnet 192.168.0.0/24 default-router '192.168.0.1'
# Set IP address lease time
set service dhcp-server shared-network-name LAN subnet 192.168.0.0/24 lease '86400'
# Set IP leases ranges
# Here clients will get IP addresses in the range 192.168.0.9 - 192.168.0.254,
set service dhcp-server shared-network-name LAN subnet 192.168.0.0/24 range 0 start 192.168.0.9
set service dhcp-server shared-network-name LAN subnet 192.168.0.0/24 range 0 stop '192.168.0.254'


# DNS settings
set service dhcp-server shared-network-name LAN subnet 192.168.0.0/24 domain-name 'vyos.net'
# set name-server
set service dhcp-server shared-network-name LAN subnet 192.168.0.0/24 name-server '192.168.0.1'

# Set cache size. Default value in 10000
set service dns forwarding cache-size '0'
# Setup DNS resolver to be VyOS
set service dns forwarding listen-address '192.168.0.1'
# Allow dns queries from the LAN subnet
set service dns forwarding allow-from '192.168.0.0/24'


#'NAT (basically an SNAT)

# Create a rule name 100 or any number. Make note that rules are processed by VyOS is numerical order.
# Outbound interface will be eth0


set nat source rule 100 outbound-interface 'eth0'
set nat source rule 100 source address '192.168.0.0/24'
# Use IP masquerade. This will set the primary IP adress of eth0 as its translation address
set nat source rule 100 translation address masquerade

