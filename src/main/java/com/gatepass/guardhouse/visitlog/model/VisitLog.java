package com.gatepass.guardhouse.visitlog.model;

import com.gatepass.guardhouse.common.model.BaseDocument;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Document(collection = "visit_logs")
public class VisitLog extends BaseDocument {

    @Indexed
    private String visitorId;
    private String residentId;
    private String guardId;

    private Instant entryTime;
    private Instant exitTime;

    private EntryMethod entryMethod;

    private String notes;
}