package org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.rev140706;
import com.google.common.collect.Range;
import java.util.Collections;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.Host;
import java.util.Map;
import java.util.HashMap;
import com.google.common.collect.ImmutableList;
import java.math.BigInteger;
import java.util.List;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.PortNumber;
import org.opendaylight.yangtools.yang.binding.Augmentation;



public class BuildConnectorInputBuilder {

    private Host _address;
    private java.lang.String _instance;
    private java.lang.String _password;
    private PortNumber _port;
    private static List<Range<BigInteger>> _port_range;
    private java.lang.String _username;

    private Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.rev140706.BuildConnectorInput>>, Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.rev140706.BuildConnectorInput>> augmentation = new HashMap<>();

    public BuildConnectorInputBuilder() {
    } 


    public Host getAddress() {
        return _address;
    }
    
    public java.lang.String getInstance() {
        return _instance;
    }
    
    public java.lang.String getPassword() {
        return _password;
    }
    
    public PortNumber getPort() {
        return _port;
    }
    
    public java.lang.String getUsername() {
        return _username;
    }
    
    @SuppressWarnings("unchecked")
    public <E extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.rev140706.BuildConnectorInput>> E getAugmentation(java.lang.Class<E> augmentationType) {
        if (augmentationType == null) {
            throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
        }
        return (E) augmentation.get(augmentationType);
    }

    public BuildConnectorInputBuilder setAddress(Host value) {
        this._address = value;
        return this;
    }
    
    public BuildConnectorInputBuilder setInstance(java.lang.String value) {
        this._instance = value;
        return this;
    }
    
    public BuildConnectorInputBuilder setPassword(java.lang.String value) {
        this._password = value;
        return this;
    }
    
    public BuildConnectorInputBuilder setPort(PortNumber value) {
        if (value != null) {
            BigInteger _constraint = BigInteger.valueOf(value.getValue());
            boolean isValidRange = false;
            for (Range<BigInteger> r : _port_range()) {
                if (r.contains(_constraint)) {
                    isValidRange = true;
                }
            }
            if (!isValidRange) {
                throw new IllegalArgumentException(String.format("Invalid range: %s, expected: %s.", value, _port_range));
            }
        }
        this._port = value;
        return this;
    }
    public static List<Range<BigInteger>> _port_range() {
        if (_port_range == null) {
            synchronized (BuildConnectorInputBuilder.class) {
                if (_port_range == null) {
                    ImmutableList.Builder<Range<BigInteger>> builder = ImmutableList.builder();
                    builder.add(Range.closed(BigInteger.ZERO, BigInteger.valueOf(65535L)));
                    _port_range = builder.build();
                }
            }
        }
        return _port_range;
    }
    
    public BuildConnectorInputBuilder setUsername(java.lang.String value) {
        this._username = value;
        return this;
    }
    
    public BuildConnectorInputBuilder addAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.rev140706.BuildConnectorInput>> augmentationType, Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.rev140706.BuildConnectorInput> augmentation) {
        this.augmentation.put(augmentationType, augmentation);
        return this;
    }

    public BuildConnectorInput build() {
        return new BuildConnectorInputImpl(this);
    }

    private static final class BuildConnectorInputImpl implements BuildConnectorInput {

        public java.lang.Class<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.rev140706.BuildConnectorInput> getImplementedInterface() {
            return org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.rev140706.BuildConnectorInput.class;
        }

        private final Host _address;
        private final java.lang.String _instance;
        private final java.lang.String _password;
        private final PortNumber _port;
        private final java.lang.String _username;

        private Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.rev140706.BuildConnectorInput>>, Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.rev140706.BuildConnectorInput>> augmentation = new HashMap<>();

        private BuildConnectorInputImpl(BuildConnectorInputBuilder builder) {
            this._address = builder.getAddress();
            this._instance = builder.getInstance();
            this._password = builder.getPassword();
            this._port = builder.getPort();
            this._username = builder.getUsername();
            switch (builder.augmentation.size()) {
             case 0:
                 this.augmentation = Collections.emptyMap();
                 break;
             case 1:
                 final Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.rev140706.BuildConnectorInput>>, Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.rev140706.BuildConnectorInput>> e = builder.augmentation.entrySet().iterator().next();
                 this.augmentation = Collections.<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.rev140706.BuildConnectorInput>>, Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.rev140706.BuildConnectorInput>>singletonMap(e.getKey(), e.getValue());
                 break;
             default :
                 this.augmentation = new HashMap<>(builder.augmentation);
             }
        }

        @Override
        public Host getAddress() {
            return _address;
        }
        
        @Override
        public java.lang.String getInstance() {
            return _instance;
        }
        
        @Override
        public java.lang.String getPassword() {
            return _password;
        }
        
        @Override
        public PortNumber getPort() {
            return _port;
        }
        
        @Override
        public java.lang.String getUsername() {
            return _username;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public <E extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.rev140706.BuildConnectorInput>> E getAugmentation(java.lang.Class<E> augmentationType) {
            if (augmentationType == null) {
                throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
            }
            return (E) augmentation.get(augmentationType);
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((_address == null) ? 0 : _address.hashCode());
            result = prime * result + ((_instance == null) ? 0 : _instance.hashCode());
            result = prime * result + ((_password == null) ? 0 : _password.hashCode());
            result = prime * result + ((_port == null) ? 0 : _port.hashCode());
            result = prime * result + ((_username == null) ? 0 : _username.hashCode());
            result = prime * result + ((augmentation == null) ? 0 : augmentation.hashCode());
            return result;
        }

        @Override
        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            BuildConnectorInputImpl other = (BuildConnectorInputImpl) obj;
            if (_address == null) {
                if (other._address != null) {
                    return false;
                }
            } else if(!_address.equals(other._address)) {
                return false;
            }
            if (_instance == null) {
                if (other._instance != null) {
                    return false;
                }
            } else if(!_instance.equals(other._instance)) {
                return false;
            }
            if (_password == null) {
                if (other._password != null) {
                    return false;
                }
            } else if(!_password.equals(other._password)) {
                return false;
            }
            if (_port == null) {
                if (other._port != null) {
                    return false;
                }
            } else if(!_port.equals(other._port)) {
                return false;
            }
            if (_username == null) {
                if (other._username != null) {
                    return false;
                }
            } else if(!_username.equals(other._username)) {
                return false;
            }
            if (augmentation == null) {
                if (other.augmentation != null) {
                    return false;
                }
            } else if(!augmentation.equals(other.augmentation)) {
                return false;
            }
            return true;
        }
        
        @Override
        public java.lang.String toString() {
            java.lang.StringBuilder builder = new java.lang.StringBuilder ("BuildConnectorInput [");
            boolean first = true;
        
            if (_address != null) {
                if (first) {
                    first = false;
                } else {
                    builder.append(", ");
                }
                builder.append("_address=");
                builder.append(_address);
             }
            if (_instance != null) {
                if (first) {
                    first = false;
                } else {
                    builder.append(", ");
                }
                builder.append("_instance=");
                builder.append(_instance);
             }
            if (_password != null) {
                if (first) {
                    first = false;
                } else {
                    builder.append(", ");
                }
                builder.append("_password=");
                builder.append(_password);
             }
            if (_port != null) {
                if (first) {
                    first = false;
                } else {
                    builder.append(", ");
                }
                builder.append("_port=");
                builder.append(_port);
             }
            if (_username != null) {
                if (first) {
                    first = false;
                } else {
                    builder.append(", ");
                }
                builder.append("_username=");
                builder.append(_username);
             }
            if (first) {
                first = false;
            } else {
                builder.append(", ");
            }
            builder.append("augmentation=");
            builder.append(augmentation.values());
            return builder.append(']').toString();
        }
    }

}
