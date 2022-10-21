interfaces {
    ethernet eth0 {
        address dhcp
        description OUTSIDE
        hw-id 0c:9e:f4:26:00:00
    }
    ethernet eth1 {
        address 192.168.0.1/24
        description INSIDE
        hw-id 0c:9e:f4:26:00:01
    }
    ethernet eth2 {
        hw-id 0c:9e:f4:26:00:02
    }
    ethernet eth3 {
        hw-id 0c:9e:f4:26:00:03
    }
    loopback lo {
    }
}
nat {
    source {
        rule 100 {
            outbound-interface eth0
            source {
                address 192.168.0.0/24
            }
            translation {
                address masquerade
            }
        }
    }
}
service {
    dhcp-server {
        shared-network-name LAN {
            subnet 192.168.0.0/24 {
                default-router 192.168.0.1
                domain-name vyos.net
                lease 86400
                name-server 192.168.0.1
                range 0 {
                    start 192.168.0.9
                    stop 192.168.0.254
                }
                static-mapping firefox {
                    ip-address 192.168.0.4
                    mac-address B6:A0:89:53:3C:0D
                }
            }
        }
    }
    dns {
        forwarding {
            allow-from 192.168.0.0/24
            cache-size 0
            listen-address 192.168.0.1
        }
    }
    ssh {
        port 22
    }
}
system {
    config-management {
        commit-revisions 100
    }
    conntrack {
        modules {
            ftp
            h323
            nfs
            pptp
            sip

            sqlnet
            tftp
        }
    }
    console {
        device ttyS0 {
            speed 115200
        }
    }
    host-name vyos
    login {
        user vyos {
            authentication {
                encrypted-password $6$QxPS.uk6mfo$9QBSo8u1FkH16gMyAVhus6fU3LOzvLR9Z9.82m3tiHFAxTtIkhaZSWssSgzt4v4dGAL8rhVQxTg0oAG9/q11h/
                plaintext-password ""
            }
        }
    }
    ntp {
        server time1.vyos.net {
        }
        server time2.vyos.net {
        }
        server time3.vyos.net {
        }
    }
    syslog {
        global {
            facility all {
                level info
            }
            facility protocols {
                level debug
            }
        }
    }
}


// Warning: Do not remove the following line.
// vyos-config-version: "bgp@3:broadcast-relay@1:cluster@1:config-management@1:conntrack@3:conntrack-sync@2:dhcp-relay@2:dhcp-server@6:dhcpv6-server@1:dns-forwarding@3:firewall@8:flow-accounting@1:https@3:ids@1:interfaces@26:ipoe-server@1:ipsec@10:isis@2:l2tp@4:lldp@1:mdns@1:monitoring@1:nat@5:nat66@1:ntp@1:openconnect@2:ospf@1:policy@4:pppoe-server@6:pptp@2:qos@1:quagga@10:rpki@1:salt@1:snmp@2:ssh@2:sstp@4:system@25:vrf@3:vrrp@3:vyos-accel-ppp@2:wanloadbalance@3:webproxy@2"
// Release version: 1.4-rolling-202210160218
