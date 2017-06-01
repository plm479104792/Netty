import socket
import time

msg_header = 'AADD'
msg_stamp = '\x00\x00\x00\x00'
msg_id_gw = 'carbonx1'
msg_id_dev = '00000000'
msg_devtype = '\x01\x00'

msg_auth_key = 'TWRT2015' 
msg_auth_datatype = '\x1c\x00'

msg_auth = msg_header+msg_stamp+msg_id_gw+msg_id_dev+msg_devtype+msg_auth_datatype+'\x00\x08'+msg_auth_key

install_id = '00000000'
install_type = '\x02\x00\x00\x00'
install_name = 'switch001' + '\x00'*51
install_pos = 'hall' + '\x00'*56
install_postype = '\x02'+'\x00'*3

msg_set_install = msg_header+msg_stamp+msg_id_gw+msg_id_dev+msg_devtype+'\x06\x00'+'\x00\x88' + install_id+install_type+install_name+install_pos+install_postype

msg_get_install = msg_header+msg_stamp+msg_id_gw+msg_id_dev+msg_devtype+'\x07\x00'+'\x00\x08' + install_id

msg_finish_install = msg_header+msg_stamp+msg_id_gw+msg_id_dev+msg_devtype+'\x0b\x00'+'\x00\x01' + '\x00'

scene_mac = '00000000'
scene_id_major = '00000011'
scene_id_minor = '00000001'
scene_type = '\x01\x00\x00\x00'
scene_name = 'scene'+'\x00'*55

scene_trigger1 = 'T0000001'*2
scene_trigger2 = 'T0000001'*2
scene_item1  = 'I0000001'*2
scene_item2  = 'I0000002'*2

scene_item_extra = '\x02\x00\x00\x00'*2

scene_mac2 = '00000111'
scene_id_major2 = '00000031'
scene_id_minor2 = '00000011'

msg_set_scene = msg_header+msg_stamp+msg_id_gw+msg_id_dev+msg_devtype+'\x0f\x00'+'\x00\x88' + scene_id_major+scene_id_minor+scene_mac+scene_type+scene_name+'\x01\x00\x00\x00'+'\x01\x00\x00\x00'+scene_trigger1+scene_item_extra+scene_item1+scene_item_extra
																		15		
msg_update_scene = msg_header+msg_stamp+msg_id_gw+msg_id_dev+msg_devtype+'\x0f\x00'+'\x00\x88' + scene_id_major+scene_id_minor+scene_mac+scene_type+scene_name+'\x00\x00\x00\x00'+'\x02\x00\x00\x00'+scene_item1+scene_item_extra+scene_item2+scene_item_extra
																		15
msg_set_scene2 = msg_header+msg_stamp+msg_id_gw+msg_id_dev+msg_devtype+'\x0f\x00'+'\x00\x88' + scene_id_major2+scene_id_minor2+scene_mac2+scene_type+scene_name+'\x01\x00\x00\x00'+'\x01\x00\x00\x00'+scene_trigger1+scene_item_extra+scene_item1+scene_item_extra
																		15
msg_del_scene = msg_header+msg_stamp+msg_id_gw+msg_id_dev+msg_devtype+'\x12\x00'+'\x00\x10' + scene_id_major+scene_id_minor
																		18
msg_	get_scene = msg_header+msg_stamp+msg_id_gw+msg_id_dev+msg_devtype+'\x0e\x00'+'\x00\x10' + scene_id_major+scene_id_minor
																		14
msg_get_scene_null = msg_header+msg_stamp+msg_id_gw+msg_id_dev+msg_devtype+'\x0e\x00'+'\x00\x10' + '\x01'+'\x00'*7+scene_id_minor
																		14
msg_finish_scene = msg_header+msg_stamp+msg_id_gw+msg_id_dev+msg_devtype+'\x11\x00'+'\x00\x01' + '\x00'
																		17
msg_scene_ctrl = msg_header+msg_stamp+msg_id_gw+msg_id_dev+msg_devtype+'\x0d\x00'+'\x00\x10'+scene_id_major+scene_id_minor
LEN_BUFF_IO = 1000														13

serverAddress = ('localhost', 9091)

skt = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
skt.connect(serverAddress)

len = skt.send(msg_auth)
msg_bak = skt.recv(1024)
print msg_bak

#len = skt.send(msg_set_install)
#msg_bak = skt.recv(1024)
#print msg_bak

#len = skt.send(msg_get_install)
#msg_bak = skt.recv(1024)
#print msg_bak


#len = skt.send(msg_finish_install)
#msg_bak = skt.recv(1024)
#print msg_bak

len = skt.send(msg_set_scene)
print len
msg_bak = skt.recv(1024)
print msg_bak


len = skt.send(msg_set_scene2)
print len
msg_bak = skt.recv(1024)
print msg_bak

len = skt.send(msg_get_scene)
print len
msg_bak = skt.recv(1024)
print msg_bak


len = skt.send(msg_scene_ctrl)
print len

len = skt.send(msg_update_scene)
print len
msg_bak = skt.recv(1024)
print msg_bak

len = skt.send(msg_get_scene)
print len
msg_bak = skt.recv(1024)
print msg_bak


len = skt.send(msg_scene_ctrl)
print len

len = skt.send(msg_finish_scene)
print len
msg_bak = skt.recv(1024)
print 'finish scene result' + msg_bak



#len = skt.send(msg_set_scene1)
#print len
#msg_bak = skt.recv(1024)
#print msg_bak




#len = skt.send(msg_get_scene_null)
#print len
#msg_bak = skt.recv(1024)
#print msg_bak

#len = skt.send(msg_finish_scene)
#print len
#msg_bak = skt.recv(1024)
#print 'finish scene result' + msg_bak

