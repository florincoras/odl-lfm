package org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.mappingservice.toaster.impl.rev140131.modules.module.configuration.toaster.provider.impl;
import java.util.Collections;
import java.util.Map;
import org.opendaylight.yangtools.yang.binding.DataObject;
import java.util.HashMap;
import org.opendaylight.yangtools.yang.binding.Augmentation;



public class DataBrokerBuilder {

    private java.lang.Object _name;
    private java.lang.Class<? extends org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.rev130405.ServiceType> _type;

    private Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.mappingservice.toaster.impl.rev140131.modules.module.configuration.toaster.provider.impl.DataBroker>>, Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.mappingservice.toaster.impl.rev140131.modules.module.configuration.toaster.provider.impl.DataBroker>> augmentation = new HashMap<>();

    public DataBrokerBuilder() {
    } 
    
    public DataBrokerBuilder(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.rev130405.ServiceRef arg) {
        this._name = arg.getName();
        this._type = arg.getType();
    }

    /**
     Set fields from given grouping argument. Valid argument is instance of one of following types:
     * <ul>
     * <li>org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.rev130405.ServiceRef</li>
     * </ul>
     *
     * @param arg grouping object
     * @throws IllegalArgumentException if given argument is none of valid types
    */
    public void fieldsFrom(DataObject arg) {
        boolean isValidArg = false;
        if (arg instanceof org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.rev130405.ServiceRef) {
            this._name = ((org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.rev130405.ServiceRef)arg).getName();
            this._type = ((org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.rev130405.ServiceRef)arg).getType();
            isValidArg = true;
        }
        if (!isValidArg) {
            throw new IllegalArgumentException(
              "expected one of: [org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.rev130405.ServiceRef] \n" +
              "but was: " + arg
            );
        }
    }

    public java.lang.Object getName() {
        return _name;
    }
    
    public java.lang.Class<? extends org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.rev130405.ServiceType> getType() {
        return _type;
    }
    
    @SuppressWarnings("unchecked")
    public <E extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.mappingservice.toaster.impl.rev140131.modules.module.configuration.toaster.provider.impl.DataBroker>> E getAugmentation(java.lang.Class<E> augmentationType) {
        if (augmentationType == null) {
            throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
        }
        return (E) augmentation.get(augmentationType);
    }

    public DataBrokerBuilder setName(java.lang.Object value) {
        this._name = value;
        return this;
    }
    
    public DataBrokerBuilder setType(java.lang.Class<? extends org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.rev130405.ServiceType> value) {
        this._type = value;
        return this;
    }
    
    public DataBrokerBuilder addAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.mappingservice.toaster.impl.rev140131.modules.module.configuration.toaster.provider.impl.DataBroker>> augmentationType, Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.mappingservice.toaster.impl.rev140131.modules.module.configuration.toaster.provider.impl.DataBroker> augmentation) {
        this.augmentation.put(augmentationType, augmentation);
        return this;
    }

    public DataBroker build() {
        return new DataBrokerImpl(this);
    }

    private static final class DataBrokerImpl implements DataBroker {

        public java.lang.Class<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.mappingservice.toaster.impl.rev140131.modules.module.configuration.toaster.provider.impl.DataBroker> getImplementedInterface() {
            return org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.mappingservice.toaster.impl.rev140131.modules.module.configuration.toaster.provider.impl.DataBroker.class;
        }

        private final java.lang.Object _name;
        private final java.lang.Class<? extends org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.rev130405.ServiceType> _type;

        private Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.mappingservice.toaster.impl.rev140131.modules.module.configuration.toaster.provider.impl.DataBroker>>, Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.mappingservice.toaster.impl.rev140131.modules.module.configuration.toaster.provider.impl.DataBroker>> augmentation = new HashMap<>();

        private DataBrokerImpl(DataBrokerBuilder builder) {
            this._name = builder.getName();
            this._type = builder.getType();
            switch (builder.augmentation.size()) {
             case 0:
                 this.augmentation = Collections.emptyMap();
                 break;
             case 1:
                 final Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.mappingservice.toaster.impl.rev140131.modules.module.configuration.toaster.provider.impl.DataBroker>>, Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.mappingservice.toaster.impl.rev140131.modules.module.configuration.toaster.provider.impl.DataBroker>> e = builder.augmentation.entrySet().iterator().next();
                 this.augmentation = Collections.<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.mappingservice.toaster.impl.rev140131.modules.module.configuration.toaster.provider.impl.DataBroker>>, Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.mappingservice.toaster.impl.rev140131.modules.module.configuration.toaster.provider.impl.DataBroker>>singletonMap(e.getKey(), e.getValue());
                 break;
             default :
                 this.augmentation = new HashMap<>(builder.augmentation);
             }
        }

        @Override
        public java.lang.Object getName() {
            return _name;
        }
        
        @Override
        public java.lang.Class<? extends org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.rev130405.ServiceType> getType() {
            return _type;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public <E extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.mappingservice.toaster.impl.rev140131.modules.module.configuration.toaster.provider.impl.DataBroker>> E getAugmentation(java.lang.Class<E> augmentationType) {
            if (augmentationType == null) {
                throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
            }
            return (E) augmentation.get(augmentationType);
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((_name == null) ? 0 : _name.hashCode());
            result = prime * result + ((_type == null) ? 0 : _type.hashCode());
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
            DataBrokerImpl other = (DataBrokerImpl) obj;
            if (_name == null) {
                if (other._name != null) {
                    return false;
                }
            } else if(!_name.equals(other._name)) {
                return false;
            }
            if (_type == null) {
                if (other._type != null) {
                    return false;
                }
            } else if(!_type.equals(other._type)) {
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
            java.lang.StringBuilder builder = new java.lang.StringBuilder ("DataBroker [");
            boolean first = true;
        
            if (_name != null) {
                if (first) {
                    first = false;
                } else {
                    builder.append(", ");
                }
                builder.append("_name=");
                builder.append(_name);
             }
            if (_type != null) {
                if (first) {
                    first = false;
                } else {
                    builder.append(", ");
                }
                builder.append("_type=");
                builder.append(_type);
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
