# reading CSV files per year

data_total_complete <- read.csv2(file="EWR200812E_Matrix.csv", head=TRUE)
# drop columns that are not interesting for us
data_total <- data_total_complete[ -c(11:42) ]

data_foreigners_complete <- read.csv2(file="EWR200812A_Matrix.csv", head=TRUE)
# drop columns that are not interesting for us
data_foreigners <- data_foreigners_complete[ -c(11:42) ]

data_migration_background_complete <- read.csv2(file="EWRMIGRA200812E_Matrix.csv", head=TRUE)
# drop columns that are not interesting for us
data_migration_background <- data_migration_background_complete[ -c(11:42) ]

data_migration_background_herkunft <- read.csv2(file="EWRMIGRA200812H_Matrix.csv", head=TRUE)

# People with migration background associated to their origins
data_merged_migration <- merge(data_migration_background, data_migration_background_herkunft, by = c("ZEIT", "RAUMID", "BEZ", "PGR", "BZR", "PLR", "STADTRAUM", "MH_E"))

# Foreigners and people with migration background combined
data_merged_foreigners_migration <- merge(data_merged_migration, data_foreigners, by = c("ZEIT", "RAUMID", "BEZ", "PGR", "BZR", "PLR", "STADTRAUM"))

# Foreigners, people with migration background and total population
data_merged_total <- merge(data_merged_foreigners_migration, data_total, by = c("ZEIT", "RAUMID", "BEZ", "PGR", "BZR", "PLR", "STADTRAUM"))

# writing merged data frame to a new CSV file
write.csv2(data_merged_total, "EWR_2008.csv", row.names = FALSE)