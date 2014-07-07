package org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.mappingservice.toaster.impl.rev140131.modules.module.configuration;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.mappingservice.toaster.impl.rev140131.modules.module.configuration.toaster.provider.impl.DataBroker;
import org.opendaylight.yangtools.yang.binding.Augmentation;



public class ToasterProviderImplBuilder {

    private DataBroker _dataBroker;

    private Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.mappingservice.toaster.impl.rev140131.modules.module.configuration.ToasterProviderImpl>>, Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.mappingservice.toaster.impl.rev140131.modules.module.configuration.ToasterProviderImpl>> augmentation = new HashMap<>();

    public ToasterProviderImplBuilder() {
    } 
    


    public DataBroker getDataBroker() {
        return _dataBroker;
    }
    
    @SuppressWarnings("unchecked")
    public <E extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.mappingservice.toaster.impl.rev140131.modules.module.configuration.ToasterProviderImpl>> E getAugmentation(java.lang.Class<E> augmentationType) {
        if (augmentationType == null) {
            throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
        }
        return (E) augmentation.get(augmentationType);
    }

    public ToasterProviderImplBuilder setDataBroker(DataBroker value) {
        this._dataBroker = value;
        return this;
    }
    
    public ToasterProviderImplBuilder addAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.mappingservice.toaster.impl.rev140131.modules.module.configuration.ToasterProviderImpl>> augmentationType, Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.mappingservice.toaster.impl.rev140131.modules.module.configuration.ToasterProviderImpl> augmentation) {
        this.augmentation.put(augmentationType, augmentation);
        return this;
    }

    public ToasterProviderImpl build() {
        return new ToasterProviderImplImpl(this);
    }

    private static final class ToasterProviderImplImpl implements ToasterProviderImpl {

        public java.lang.Class<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.mappingservice.toaster.impl.rev140131.modules.module.configuration.ToasterProviderImpl> getImplementedInterface() {
            return org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.mappingservice.toaster.impl.rev140131.modules.module.configuration.ToasterProviderImpl.class;
        }

        private final DataBroker _dataBroker;

        private Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.mappingservice.toaster.impl.rev140131.modules.module.configuration.ToasterProviderImpl>>, Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.mappingservice.toaster.impl.rev140131.modules.module.configuration.ToasterProviderImpl>> augmentation = new HashMap<>();

        private ToasterProviderImplImpl(ToasterProviderImplBuilder builder) {
            this._dataBroker = builder.getDataBroker();
            switch (builder.augmentation.size()) {
             case 0:
                 this.augmentation = Collections.emptyMap();
                 break;
             case 1:
                 final Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.mappingservice.toaster.impl.rev140131.modules.module.configuration.ToasterProviderImpl>>, Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.mappingservice.toaster.impl.rev140131.modules.module.configuration.ToasterProviderImpl>> e = builder.augmentation.entrySet().iterator().next();
                 this.augmentation = Collections.<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.mappingservice.toaster.impl.rev140131.modules.module.configuration.ToasterProviderImpl>>, Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.mappingservice.toaster.impl.rev140131.modules.module.configuration.ToasterProviderImpl>>singletonMap(e.getKey(), e.getValue());
                 break;
             default :
                 this.augmentation = new HashMap<>(builder.augmentation);
             }
        }

        @Override
        public DataBroker getDataBroker() {
            return _dataBroker;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public <E extends Augmentation<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.mappingservice.toaster.impl.rev140131.modules.module.configuration.ToasterProviderImpl>> E getAugmentation(java.lang.Class<E> augmentationType) {
            if (augmentationType == null) {
                throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
            }
            return (E) augmentation.get(augmentationType);
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((_dataBroker == null) ? 0 : _dataBroker.hashCode());
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
            ToasterProviderImplImpl other = (ToasterProviderImplImpl) obj;
            if (_dataBroker == null) {
                if (other._dataBroker != null) {
                    return false;
                }
            } else if(!_dataBroker.equals(other._dataBroker)) {
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
            java.lang.StringBuilder builder = new java.lang.StringBuilder ("ToasterProviderImpl [");
            boolean first = true;
        
            if (_dataBroker != null) {
                if (first) {
                    first = false;
                } else {
                    builder.append(", ");
                }
                builder.append("_dataBroker=");
                builder.append(_dataBroker);
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
