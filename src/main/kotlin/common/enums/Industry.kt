package app.stocklens.domain.enums

import kotlinx.serialization.Serializable

@Serializable
enum class Industry(
    val industryName: String,
) {
    STEEL("Steel"),
    SILVER("Silver"),
    OTHER_PRECIOUS_METALS("Other Precious Metals"),
    GOLD("Gold"),
    COPPER("Copper"),
    ALUMINUM("Aluminum"),
    PAPER_LUMBER_FOREST_PRODUCTS("Paper, Lumber & Forest Products"),
    INDUSTRIAL_MATERIALS("Industrial Materials"),
    CONSTRUCTION_MATERIALS("Construction Materials"),
    CHEMICALS_SPECIALTY("Chemicals - Specialty"),
    CHEMICALS("Chemicals"),
    AGRICULTURAL_INPUTS("Agricultural Inputs"),
    TELECOMMUNICATIONS_SERVICES("Telecommunications Services"),
    INTERNET_CONTENT_INFORMATION("Internet Content & Information"),
    PUBLISHING("Publishing"),
    BROADCASTING("Broadcasting"),
    ADVERTISING_AGENCIES("Advertising Agencies"),
    ENTERTAINMENT("Entertainment"),
    TRAVEL_LODGING("Travel Lodging"),
    TRAVEL_SERVICES("Travel Services"),
    SPECIALTY_RETAIL("Specialty Retail"),
    LUXURY_GOODS("Luxury Goods"),
    HOME_IMPROVEMENT("Home Improvement"),
    RESIDENTIAL_CONSTRUCTION("Residential Construction"),
    DEPARTMENT_STORES("Department Stores"),
    PERSONAL_PRODUCTS_SERVICES("Personal Products & Services"),
    LEISURE("Leisure"),
    GAMBLING_RESORTS_CASINOS("Gambling, Resorts & Casinos"),
    FURNISHINGS_FIXTURES_APPLIANCES("Furnishings, Fixtures & Appliances"),
    RESTAURANTS("Restaurants"),
    AUTO_PARTS("Auto - Parts"),
    AUTO_MANUFACTURERS("Auto - Manufacturers"),
    AUTO_RECREATIONAL_VEHICLES("Auto - Recreational Vehicles"),
    AUTO_DEALERSHIPS("Auto - Dealerships"),
    APPAREL_RETAIL("Apparel - Retail"),
    APPAREL_MANUFACTURERS("Apparel - Manufacturers"),
    APPAREL_FOOTWEAR_ACCESSORIES("Apparel - Footwear & Accessories"),
    PACKAGING_CONTAINERS("Packaging & Containers"),
    TOBACCO("Tobacco"),
    GROCERY_STORES("Grocery Stores"),
    DISCOUNT_STORES("Discount Stores"),
    HOUSEHOLD_PERSONAL_PRODUCTS("Household & Personal Products"),
    PACKAGED_FOODS("Packaged Foods"),
    FOOD_DISTRIBUTION("Food Distribution"),
    FOOD_CONFECTIONERS("Food Confectioners"),
    AGRICULTURAL_FARM_PRODUCTS("Agricultural Farm Products"),
    EDUCATION_TRAINING_SERVICES("Education & Training Services"),
    BEVERAGES_WINERIES_DISTILLERIES("Beverages - Wineries & Distilleries"),
    BEVERAGES_NON_ALCOHOLIC("Beverages - Non-Alcoholic"),
    BEVERAGES_ALCOHOLIC("Beverages - Alcoholic"),
    URANIUM("Uranium"),
    SOLAR("Solar"),
    OIL_GAS_REFINING_MARKETING("Oil & Gas Refining & Marketing"),
    OIL_GAS_MIDSTREAM("Oil & Gas Midstream"),
    OIL_GAS_INTEGRATED("Oil & Gas Integrated"),
    OIL_GAS_EXPLORATION_PRODUCTION("Oil & Gas Exploration & Production"),
    OIL_GAS_EQUIPMENT_SERVICES("Oil & Gas Equipment & Services"),
    OIL_GAS_ENERGY("Oil & Gas Energy"),
    OIL_GAS_DRILLING("Oil & Gas Drilling"),
    COAL("Coal"),
    SHELL_COMPANIES("Shell Companies"),
    INVESTMENT_BANKING_INVESTMENT_SERVICES("Investment - Banking & Investment Services"),
    INSURANCE_SPECIALTY("Insurance - Specialty"),
    INSURANCE_REINSURANCE("Insurance - Reinsurance"),
    INSURANCE_PROPERTY_CASUALTY("Insurance - Property & Casualty"),
    INSURANCE_LIFE("Insurance - Life"),
    INSURANCE_DIVERSIFIED("Insurance - Diversified"),
    INSURANCE_BROKERS("Insurance - Brokers"),
    FINANCIAL_MORTGAGES("Financial - Mortgages"),
    FINANCIAL_DIVERSIFIED("Financial - Diversified"),
    FINANCIAL_DATA_STOCK_EXCHANGES("Financial - Data & Stock Exchanges"),
    FINANCIAL_CREDIT_SERVICES("Financial - Credit Services"),
    FINANCIAL_CONGLOMERATES("Financial - Conglomerates"),
    FINANCIAL_CAPITAL_MARKETS("Financial - Capital Markets"),
    BANKS_REGIONAL("Banks - Regional"),
    BANKS_DIVERSIFIED("Banks - Diversified"),
    BANKS("Banks"),
    ASSET_MANAGEMENT("Asset Management"),
    ASSET_MANAGEMENT_BONDS("Asset Management - Bonds"),
    ASSET_MANAGEMENT_INCOME("Asset Management - Income"),
    ASSET_MANAGEMENT_LEVERAGED("Asset Management - Leveraged"),
    ASSET_MANAGEMENT_CRYPTOCURRENCY("Asset Management - Cryptocurrency"),
    ASSET_MANAGEMENT_GLOBAL("Asset Management - Global"),
    MEDICAL_SPECIALTIES("Medical - Specialties"),
    MEDICAL_PHARMACEUTICALS("Medical - Pharmaceuticals"),
    MEDICAL_INSTRUMENTS_SUPPLIES("Medical - Instruments & Supplies"),
    MEDICAL_HEALTHCARE_PLANS("Medical - Healthcare Plans"),
    MEDICAL_HEALTHCARE_INFORMATION_SERVICES("Medical - Healthcare Information Services"),
    MEDICAL_EQUIPMENT_SERVICES("Medical - Equipment & Services"),
    MEDICAL_DISTRIBUTION("Medical - Distribution"),
    MEDICAL_DIAGNOSTICS_RESEARCH("Medical - Diagnostics & Research"),
    MEDICAL_DEVICES("Medical - Devices"),
    MEDICAL_CARE_FACILITIES("Medical - Care Facilities"),
    DRUG_MANUFACTURERS_SPECIALTY_GENERIC("Drug Manufacturers - Specialty & Generic"),
    DRUG_MANUFACTURERS_GENERAL("Drug Manufacturers - General"),
    BIOTECHNOLOGY("Biotechnology"),
    WASTE_MANAGEMENT("Waste Management"),
    TRUCKING("Trucking"),
    RAILROADS("Railroads"),
    AEROSPACE_DEFENSE("Aerospace & Defense"),
    MARINE_SHIPPING("Marine Shipping"),
    INTEGRATED_FREIGHT_LOGISTICS("Integrated Freight & Logistics"),
    AIRLINES_AIRPORTS_AIR_SERVICES("Airlines, Airports & Air Services"),
    GENERAL_TRANSPORTATION("General Transportation"),
    MANUFACTURING_TOOLS_ACCESSORIES("Manufacturing - Tools & Accessories"),
    MANUFACTURING_TEXTILES("Manufacturing - Textiles"),
    MANUFACTURING_MISCELLANEOUS("Manufacturing - Miscellaneous"),
    MANUFACTURING_METAL_FABRICATION("Manufacturing - Metal Fabrication"),
    INDUSTRIAL_DISTRIBUTION("Industrial - Distribution"),
    INDUSTRIAL_SPECIALTIES("Industrial - Specialties"),
    INDUSTRIAL_POLLUTION_TREATMENT_CONTROLS("Industrial - Pollution & Treatment Controls"),
    ENVIRONMENTAL_SERVICES("Environmental Services"),
    INDUSTRIAL_MACHINERY("Industrial - Machinery"),
    INDUSTRIAL_INFRASTRUCTURE_OPERATIONS("Industrial - Infrastructure Operations"),
    INDUSTRIAL_CAPITAL_GOODS("Industrial - Capital Goods"),
    CONSULTING_SERVICES("Consulting Services"),
    BUSINESS_EQUIPMENT_SUPPLIES("Business Equipment & Supplies"),
    STAFFING_EMPLOYMENT_SERVICES("Staffing & Employment Services"),
    RENTAL_LEASING_SERVICES("Rental & Leasing Services"),
    ENGINEERING_CONSTRUCTION("Engineering & Construction"),
    SECURITY_PROTECTION_SERVICES("Security & Protection Services"),
    SPECIALTY_BUSINESS_SERVICES("Specialty Business Services"),
    CONSTRUCTION("Construction"),
    CONGLOMERATES("Conglomerates"),
    ELECTRICAL_EQUIPMENT_PARTS("Electrical Equipment & Parts"),
    AGRICULTURAL_MACHINERY("Agricultural - Machinery"),
    AGRICULTURAL_COMMODITIES_MILLING("Agricultural - Commodities/Milling"),
    REIT_SPECIALTY("REIT - Specialty"),
    REIT_RETAIL("REIT - Retail"),
    REIT_RESIDENTIAL("REIT - Residential"),
    REIT_OFFICE("REIT - Office"),
    REIT_MORTGAGE("REIT - Mortgage"),
    REIT_INDUSTRIAL("REIT - Industrial"),
    REIT_HOTEL_MOTEL("REIT - Hotel & Motel"),
    REIT_HEALTHCARE_FACILITIES("REIT - Healthcare Facilities"),
    REIT_DIVERSIFIED("REIT - Diversified"),
    REAL_ESTATE_SERVICES("Real Estate - Services"),
    REAL_ESTATE_DIVERSIFIED("Real Estate - Diversified"),
    REAL_ESTATE_DEVELOPMENT("Real Estate - Development"),
    REAL_ESTATE_GENERAL("Real Estate - General"),
    INFORMATION_TECHNOLOGY_SERVICES("Information Technology Services"),
    HARDWARE_EQUIPMENT_PARTS("Hardware, Equipment & Parts"),
    COMPUTER_HARDWARE("Computer Hardware"),
    ELECTRONIC_GAMING_MULTIMEDIA("Electronic Gaming & Multimedia"),
    SOFTWARE_SERVICES("Software - Services"),
    SOFTWARE_INFRASTRUCTURE("Software - Infrastructure"),
    SOFTWARE_APPLICATION("Software - Application"),
    SEMICONDUCTORS("Semiconductors"),
    MEDIA_ENTERTAINMENT("Media & Entertainment"),
    COMMUNICATION_EQUIPMENT("Communication Equipment"),
    TECHNOLOGY_DISTRIBUTORS("Technology Distributors"),
    CONSUMER_ELECTRONICS("Consumer Electronics"),
    RENEWABLE_UTILITIES("Renewable Utilities"),
    REGULATED_WATER("Regulated Water"),
    REGULATED_GAS("Regulated Gas"),
    REGULATED_ELECTRIC("Regulated Electric"),
    INDEPENDENT_POWER_PRODUCERS("Independent Power Producers"),
    DIVERSIFIED_UTILITIES("Diversified Utilities"),
    GENERAL_UTILITIES("General Utilities"),
    UNKNOWN("Unknown");

    /**
     * Determines if this industry is typically capital intensive.
     * Capital intensive industries require significant physical assets and infrastructure
     * to operate, leading to high fixed costs and lower asset turnover.
     */
    fun isCapitalIntensive(): Boolean {
        return this in capitalIntensiveIndustries
    }

    /**
     * Determines if this industry is typically asset light.
     * Asset light industries require minimal physical assets to operate,
     * often relying more on intellectual property, human capital, or digital infrastructure.
     */
    fun isAssetLight(): Boolean {
        return this in assetLightIndustries
    }

    fun hasInventory(): Boolean {
        return this in hasInventoryIndustries
    }

    fun isLowVolatility(): Boolean {
        return this in lowVolatilityIndustries
    }

    fun isHighVolatility(): Boolean {
        return this in highVolatilityIndustries
    }

    fun isFinancial(): Boolean {
        return this in financialIndustries
    }

    fun isTechnology(): Boolean {
        return this in technologyIndustries
    }

    fun isSoftware(): Boolean {
        return this in softwareIndustries
    }

    fun isUtilities(): Boolean {
        return this in utilitiesIndustries
    }

    fun isCyclical(): Boolean {
        return this in cyclicalIndustries
    }

    fun isIndustrial(): Boolean {
        return this in industrialIndustries
    }

    fun isCommodity(): Boolean {
        return this in commodityIndustries
    }

    fun isEnergy(): Boolean {
        return this in energyIndustries
    }

    fun isRetail(): Boolean {
        return this in retailIndustries
    }

    fun isConsumerStaples(): Boolean {
        return this in consumerStaples
    }

    fun isConsumerDiscretionary(): Boolean {
        return this in consumerDiscretionaryIndustries
    }

    fun isTravel(): Boolean {
        return this in travelIndustries
    }

    fun isHealthcare(): Boolean {
        return this in healthCareIndustries
    }

    fun isAutomotive(): Boolean {
        return this == AUTO_MANUFACTURERS ||
                this == AUTO_PARTS ||
                this == AUTO_DEALERSHIPS
    }

    /**
     * Determines if this industry is typically media-related.
     */
    fun isMedia(): Boolean {
        return this == MEDIA_ENTERTAINMENT ||
                this == BROADCASTING ||
                this == PUBLISHING ||
                this == INTERNET_CONTENT_INFORMATION
    }

    /**
     * Determines if this industry is restaurant-related.
     */
    fun isRestaurant(): Boolean {
        return this == RESTAURANTS
    }

    /**
     * Determines if this industry is manufacturing-focused.
     */
    fun isManufacturing(): Boolean {
        return this == MANUFACTURING_METAL_FABRICATION ||
                this == MANUFACTURING_MISCELLANEOUS ||
                this == MANUFACTURING_TEXTILES ||
                this == MANUFACTURING_TOOLS_ACCESSORIES ||
                this == INDUSTRIAL_MACHINERY
    }

    /**
     * Determines if this industry is oil & gas related.
     */
    fun isOil(): Boolean {
        return this == OIL_GAS_DRILLING ||
                this == OIL_GAS_EQUIPMENT_SERVICES ||
                this == OIL_GAS_INTEGRATED ||
                this == OIL_GAS_MIDSTREAM ||
                this == OIL_GAS_REFINING_MARKETING ||
                this == OIL_GAS_EXPLORATION_PRODUCTION
    }

    /**
     * Determines if this industry is communications-related.
     */
    fun isCommunication(): Boolean {
        return this == COMMUNICATION_EQUIPMENT ||
                this == TELECOMMUNICATIONS_SERVICES
    }

    /**
     * Determines if this industry is real estate related.
     */
    fun isRealEstate(): Boolean {
        return this in realEstateIndustries
    }

    fun isBank(): Boolean {
        return this == BANKS || this == BANKS_DIVERSIFIED || this == BANKS_REGIONAL
    }

    /**
     * Helper extension to identify mining industries
     */
    fun isMining(): Boolean {
        return this == GOLD ||
                this == SILVER ||
                this == COPPER ||
                this == OTHER_PRECIOUS_METALS ||
                this == ALUMINUM ||
                this == STEEL
    }

    companion object {
        fun fromIndustryName(name: String?): Industry {
            if (name == null) {
                return UNKNOWN
            }
            return entries.firstOrNull { it.industryName.equals(name, ignoreCase = true) } ?: UNKNOWN
        }

        /**
         * Industries that typically require high capital investment and fixed assets
         */
        private val capitalIntensiveIndustries = setOf(
            // Energy
            URANIUM, COAL, SOLAR,
            OIL_GAS_DRILLING, OIL_GAS_EQUIPMENT_SERVICES, OIL_GAS_INTEGRATED,
            OIL_GAS_MIDSTREAM, OIL_GAS_REFINING_MARKETING, OIL_GAS_EXPLORATION_PRODUCTION,
            OIL_GAS_ENERGY,

            // Metals & Materials
            STEEL, ALUMINUM, COPPER, GOLD, SILVER, OTHER_PRECIOUS_METALS,
            PAPER_LUMBER_FOREST_PRODUCTS, CHEMICALS, CONSTRUCTION_MATERIALS,

            // Utilities
            REGULATED_ELECTRIC, REGULATED_GAS, REGULATED_WATER, RENEWABLE_UTILITIES,
            INDEPENDENT_POWER_PRODUCERS, DIVERSIFIED_UTILITIES, GENERAL_UTILITIES,

            // Transportation
            AIRLINES_AIRPORTS_AIR_SERVICES, RAILROADS, MARINE_SHIPPING, TRUCKING,

            // Manufacturing
            AUTO_MANUFACTURERS, INDUSTRIAL_MACHINERY, INDUSTRIAL_CAPITAL_GOODS,
            MANUFACTURING_METAL_FABRICATION, MANUFACTURING_TOOLS_ACCESSORIES,

            // Real Estate
            REIT_INDUSTRIAL, REIT_HOTEL_MOTEL, REIT_RESIDENTIAL, REIT_RETAIL,
            REIT_OFFICE, REIT_HEALTHCARE_FACILITIES, REIT_SPECIALTY, REIT_DIVERSIFIED,
            REAL_ESTATE_DEVELOPMENT, REAL_ESTATE_DIVERSIFIED, REAL_ESTATE_GENERAL,

            // Other
            WASTE_MANAGEMENT, INDUSTRIAL_INFRASTRUCTURE_OPERATIONS, TELECOMMUNICATIONS_SERVICES
        )

        /**
         * Industries that typically operate with minimal physical assets
         */
        private val assetLightIndustries = setOf(
            // Technology & Software
            SOFTWARE_APPLICATION, SOFTWARE_INFRASTRUCTURE, SOFTWARE_SERVICES,
            INFORMATION_TECHNOLOGY_SERVICES, ELECTRONIC_GAMING_MULTIMEDIA,

            // Content & Digital Services
            INTERNET_CONTENT_INFORMATION, MEDIA_ENTERTAINMENT,

            // Professional Services
            ADVERTISING_AGENCIES, CONSULTING_SERVICES, SPECIALTY_BUSINESS_SERVICES,
            STAFFING_EMPLOYMENT_SERVICES, SECURITY_PROTECTION_SERVICES,

            // Financial Services
            ASSET_MANAGEMENT, ASSET_MANAGEMENT_BONDS, ASSET_MANAGEMENT_INCOME,
            ASSET_MANAGEMENT_LEVERAGED, ASSET_MANAGEMENT_CRYPTOCURRENCY, ASSET_MANAGEMENT_GLOBAL,
            FINANCIAL_DATA_STOCK_EXCHANGES,

            // Healthcare Services
            BIOTECHNOLOGY, MEDICAL_HEALTHCARE_INFORMATION_SERVICES,

            // Education
            EDUCATION_TRAINING_SERVICES
        )

        val hasInventoryIndustries = setOf(
            // Financial Services
            BANKS, BANKS_DIVERSIFIED, BANKS_REGIONAL,
            INSURANCE_BROKERS, INSURANCE_DIVERSIFIED, INSURANCE_LIFE,
            INSURANCE_PROPERTY_CASUALTY, INSURANCE_REINSURANCE, INSURANCE_SPECIALTY,
            FINANCIAL_CONGLOMERATES, FINANCIAL_CREDIT_SERVICES,
            FINANCIAL_DIVERSIFIED, FINANCIAL_MORTGAGES,
            ASSET_MANAGEMENT, ASSET_MANAGEMENT_BONDS, ASSET_MANAGEMENT_INCOME,
            ASSET_MANAGEMENT_LEVERAGED, ASSET_MANAGEMENT_CRYPTOCURRENCY, ASSET_MANAGEMENT_GLOBAL,

            // Software and Digital Services
            SOFTWARE_APPLICATION, SOFTWARE_INFRASTRUCTURE, SOFTWARE_SERVICES,
            INFORMATION_TECHNOLOGY_SERVICES, INTERNET_CONTENT_INFORMATION,
            ELECTRONIC_GAMING_MULTIMEDIA,

            // Professional Services
            CONSULTING_SERVICES, SPECIALTY_BUSINESS_SERVICES,
            STAFFING_EMPLOYMENT_SERVICES,

            // Real Estate
            REIT_SPECIALTY, REIT_RETAIL, REIT_RESIDENTIAL, REIT_OFFICE,
            REIT_MORTGAGE, REIT_INDUSTRIAL, REIT_HOTEL_MOTEL,
            REIT_HEALTHCARE_FACILITIES, REIT_DIVERSIFIED,
            REAL_ESTATE_SERVICES, REAL_ESTATE_DIVERSIFIED, REAL_ESTATE_DEVELOPMENT,

            // Utilities
            REGULATED_WATER, REGULATED_GAS, REGULATED_ELECTRIC,
            INDEPENDENT_POWER_PRODUCERS, DIVERSIFIED_UTILITIES, GENERAL_UTILITIES,

            // Others
            SHELL_COMPANIES, MEDICAL_HEALTHCARE_INFORMATION_SERVICES,
            EDUCATION_TRAINING_SERVICES
        )

        val lowVolatilityIndustries = setOf(
            REGULATED_ELECTRIC, REGULATED_GAS, REGULATED_WATER,
            DIVERSIFIED_UTILITIES, GENERAL_UTILITIES, RENEWABLE_UTILITIES,
            REIT_DIVERSIFIED, REIT_HEALTHCARE_FACILITIES, REIT_INDUSTRIAL,
            REIT_OFFICE, REIT_RESIDENTIAL, REIT_RETAIL,
            HOUSEHOLD_PERSONAL_PRODUCTS, PACKAGED_FOODS, GROCERY_STORES,
            DISCOUNT_STORES, TOBACCO, INSURANCE_DIVERSIFIED,
            TELECOMMUNICATIONS_SERVICES
        )

        val highVolatilityIndustries = setOf(
            BIOTECHNOLOGY, DRUG_MANUFACTURERS_SPECIALTY_GENERIC,
            SOFTWARE_APPLICATION, SOFTWARE_INFRASTRUCTURE,
            SEMICONDUCTORS, ELECTRONIC_GAMING_MULTIMEDIA,
            SOLAR, URANIUM, COAL,
            OIL_GAS_EXPLORATION_PRODUCTION, OIL_GAS_EQUIPMENT_SERVICES,
            STEEL, COPPER, GOLD, SILVER,
            OTHER_PRECIOUS_METALS
        )

        val financialIndustries = setOf(
            BANKS, BANKS_DIVERSIFIED, BANKS_REGIONAL,
            INSURANCE_BROKERS, INSURANCE_DIVERSIFIED, INSURANCE_LIFE,
            INSURANCE_PROPERTY_CASUALTY, INSURANCE_REINSURANCE, INSURANCE_SPECIALTY,
            FINANCIAL_CONGLOMERATES, FINANCIAL_CREDIT_SERVICES,
            FINANCIAL_DATA_STOCK_EXCHANGES, FINANCIAL_DIVERSIFIED, FINANCIAL_MORTGAGES,
            ASSET_MANAGEMENT, ASSET_MANAGEMENT_BONDS, ASSET_MANAGEMENT_INCOME,
            ASSET_MANAGEMENT_LEVERAGED, ASSET_MANAGEMENT_CRYPTOCURRENCY, ASSET_MANAGEMENT_GLOBAL
        )

        val technologyIndustries = setOf(
            SOFTWARE_APPLICATION, SOFTWARE_INFRASTRUCTURE, SOFTWARE_SERVICES,
            INFORMATION_TECHNOLOGY_SERVICES, ELECTRONIC_GAMING_MULTIMEDIA,
            SEMICONDUCTORS, COMPUTER_HARDWARE, HARDWARE_EQUIPMENT_PARTS,
            COMMUNICATION_EQUIPMENT, CONSUMER_ELECTRONICS, TECHNOLOGY_DISTRIBUTORS
        )

        val softwareIndustries = setOf(
            SOFTWARE_APPLICATION, SOFTWARE_INFRASTRUCTURE, SOFTWARE_SERVICES
        )

        val utilitiesIndustries = setOf(
            REGULATED_ELECTRIC, REGULATED_GAS, REGULATED_WATER,
            RENEWABLE_UTILITIES, DIVERSIFIED_UTILITIES, GENERAL_UTILITIES,
            INDEPENDENT_POWER_PRODUCERS
        )

        val realEstateIndustries = setOf(
            REAL_ESTATE_DEVELOPMENT,
            REAL_ESTATE_DIVERSIFIED,
            REAL_ESTATE_SERVICES,
            REIT_DIVERSIFIED,
            REIT_HEALTHCARE_FACILITIES,
            REIT_HOTEL_MOTEL,
            REIT_INDUSTRIAL,
            REIT_MORTGAGE,
            REIT_OFFICE,
            REIT_RESIDENTIAL,
            REIT_RETAIL,
            REIT_SPECIALTY
        )

        val cyclicalIndustries = setOf(
            AUTO_MANUFACTURERS, AUTO_PARTS, AUTO_DEALERSHIPS,
            RESIDENTIAL_CONSTRUCTION, FURNISHINGS_FIXTURES_APPLIANCES,
            TRAVEL_LODGING, TRAVEL_SERVICES, RESTAURANTS,
            LEISURE, GAMBLING_RESORTS_CASINOS,
            APPAREL_RETAIL, APPAREL_MANUFACTURERS, APPAREL_FOOTWEAR_ACCESSORIES, // Apparel often cyclical
            HOME_IMPROVEMENT, SPECIALTY_RETAIL, DEPARTMENT_STORES, LUXURY_GOODS // Retail often cyclical
        )

        private val commodityIndustries = setOf(
            STEEL, ALUMINUM, COPPER, GOLD, SILVER,
            OTHER_PRECIOUS_METALS, OIL_GAS_DRILLING, OIL_GAS_EQUIPMENT_SERVICES,
            OIL_GAS_INTEGRATED, OIL_GAS_MIDSTREAM, OIL_GAS_REFINING_MARKETING,
            OIL_GAS_EXPLORATION_PRODUCTION, COAL, URANIUM, SOLAR,
            CHEMICALS, CHEMICALS_SPECIALTY, // Chemicals often commodity-based
            PAPER_LUMBER_FOREST_PRODUCTS, // Often commodity-based
            AGRICULTURAL_INPUTS, AGRICULTURAL_MACHINERY, AGRICULTURAL_FARM_PRODUCTS,
            AGRICULTURAL_COMMODITIES_MILLING // Agriculture often commodity-based
        )

        val energyIndustries = setOf(
            // Energy Production & Services
            URANIUM, COAL, SOLAR,
            OIL_GAS_DRILLING, OIL_GAS_EQUIPMENT_SERVICES, OIL_GAS_INTEGRATED,
            OIL_GAS_MIDSTREAM, OIL_GAS_REFINING_MARKETING, OIL_GAS_EXPLORATION_PRODUCTION,
            OIL_GAS_ENERGY,

            // Related Utilities sometimes grouped here, but often separate
            // RENEWABLE_UTILITIES, INDEPENDENT_POWER_PRODUCERS
        )

        val retailIndustries = setOf(
            APPAREL_RETAIL, SPECIALTY_RETAIL, DEPARTMENT_STORES,
            GROCERY_STORES, DISCOUNT_STORES, HOME_IMPROVEMENT, AUTO_DEALERSHIPS
        )

        /**
         * Industries considered Consumer Staples - essential goods people buy regardless of economic conditions.
         * These are typically less cyclical.
         */
        val consumerStaples = setOf(
            // Food & Beverage Production & Distribution
            PACKAGED_FOODS,
            FOOD_DISTRIBUTION,
            FOOD_CONFECTIONERS,
            BEVERAGES_WINERIES_DISTILLERIES,
            BEVERAGES_NON_ALCOHOLIC,
            BEVERAGES_ALCOHOLIC,

            // Household & Personal Products
            HOUSEHOLD_PERSONAL_PRODUCTS,
            PERSONAL_PRODUCTS_SERVICES, // Can overlap, but kept based on enum definition
            TOBACCO,

            // Food & Staples Retailing
            GROCERY_STORES,
            DISCOUNT_STORES // Often sell a high proportion of staples
        )

        val consumerDiscretionaryIndustries = setOf(
            ENTERTAINMENT,
            TRAVEL_LODGING,
            TRAVEL_SERVICES,
            SPECIALTY_RETAIL,
            LUXURY_GOODS,
            HOME_IMPROVEMENT,
            RESIDENTIAL_CONSTRUCTION,
            DEPARTMENT_STORES,
            PERSONAL_PRODUCTS_SERVICES,
            LEISURE,
            GAMBLING_RESORTS_CASINOS,
            FURNISHINGS_FIXTURES_APPLIANCES,
            RESTAURANTS,
            AUTO_PARTS,
            AUTO_MANUFACTURERS,
            AUTO_RECREATIONAL_VEHICLES,
            AUTO_DEALERSHIPS,
            APPAREL_RETAIL,
            APPAREL_MANUFACTURERS,
            APPAREL_FOOTWEAR_ACCESSORIES,
            BROADCASTING,
            ADVERTISING_AGENCIES,
            PUBLISHING,
            ELECTRONIC_GAMING_MULTIMEDIA,
            CONSUMER_ELECTRONICS,
            REIT_HOTEL_MOTEL
        )

        val travelIndustries = setOf(
            TRAVEL_LODGING,
            TRAVEL_SERVICES,
            AIRLINES_AIRPORTS_AIR_SERVICES,
            GAMBLING_RESORTS_CASINOS,
            RESTAURANTS,
            LEISURE,
            REIT_HOTEL_MOTEL
        )

        val healthCareIndustries = setOf(
            MEDICAL_SPECIALTIES,
            MEDICAL_PHARMACEUTICALS,
            MEDICAL_INSTRUMENTS_SUPPLIES,
            MEDICAL_HEALTHCARE_PLANS,
            MEDICAL_HEALTHCARE_INFORMATION_SERVICES,
            MEDICAL_EQUIPMENT_SERVICES,
            MEDICAL_DISTRIBUTION,
            MEDICAL_DIAGNOSTICS_RESEARCH,
            MEDICAL_DEVICES,
            MEDICAL_CARE_FACILITIES,
            DRUG_MANUFACTURERS_SPECIALTY_GENERIC,
            DRUG_MANUFACTURERS_GENERAL,
            BIOTECHNOLOGY
        )

        val industrialIndustries = setOf(
            INDUSTRIAL_MATERIALS,
            INDUSTRIAL_DISTRIBUTION,
            INDUSTRIAL_SPECIALTIES,
            INDUSTRIAL_POLLUTION_TREATMENT_CONTROLS,
            INDUSTRIAL_MACHINERY,
            INDUSTRIAL_INFRASTRUCTURE_OPERATIONS,
            INDUSTRIAL_CAPITAL_GOODS,
            REIT_INDUSTRIAL
        )

        val capitalMarketsFinancialIndustries = setOf(
            INVESTMENT_BANKING_INVESTMENT_SERVICES,
            FINANCIAL_DATA_STOCK_EXCHANGES,
            FINANCIAL_CREDIT_SERVICES,
            FINANCIAL_CAPITAL_MARKETS,
            ASSET_MANAGEMENT,
            ASSET_MANAGEMENT_BONDS,
            ASSET_MANAGEMENT_INCOME,
            ASSET_MANAGEMENT_LEVERAGED,
            ASSET_MANAGEMENT_CRYPTOCURRENCY,
            ASSET_MANAGEMENT_GLOBAL
        )

        val basicMaterialsIndustries = setOf(
            STEEL,
            SILVER,
            OTHER_PRECIOUS_METALS,
            GOLD,
            COPPER,
            ALUMINUM,
            PAPER_LUMBER_FOREST_PRODUCTS,
            INDUSTRIAL_MATERIALS,
            CONSTRUCTION_MATERIALS,
            CHEMICALS_SPECIALTY,
            CHEMICALS,
            AGRICULTURAL_INPUTS,
            PACKAGING_CONTAINERS
        )

        val metalIndustries =
            setOf(STEEL, COPPER, ALUMINUM)
        val chemicalIndustries =
            setOf(CHEMICALS_SPECIALTY, CHEMICALS)
        val stableCommIndustries =
            setOf(TELECOMMUNICATIONS_SERVICES, BROADCASTING, PUBLISHING)
        val growthCommIndustries =
            setOf(INTERNET_CONTENT_INFORMATION, ENTERTAINMENT, ADVERTISING_AGENCIES)
        val luxuryApparelIndustries = setOf(LUXURY_GOODS, APPAREL_FOOTWEAR_ACCESSORIES)

        val nonRenewableEnergyIndustries = setOf(
            URANIUM,
            OIL_GAS_REFINING_MARKETING,
            OIL_GAS_MIDSTREAM,
            OIL_GAS_INTEGRATED,
            OIL_GAS_EXPLORATION_PRODUCTION,
            OIL_GAS_EQUIPMENT_SERVICES,
            OIL_GAS_ENERGY,
            OIL_GAS_DRILLING,
            COAL
        )
        val renewableEnergyIndustries = setOf(SOLAR, RENEWABLE_UTILITIES)

        val softwareAndServicesTech = setOf(
            INFORMATION_TECHNOLOGY_SERVICES,
            SOFTWARE_SERVICES,
            SOFTWARE_INFRASTRUCTURE,
            SOFTWARE_APPLICATION,
            ELECTRONIC_GAMING_MULTIMEDIA,
            MEDIA_ENTERTAINMENT
        )

        val hardwareAndEquipmentTech = setOf(
            HARDWARE_EQUIPMENT_PARTS,
            COMPUTER_HARDWARE,
            COMMUNICATION_EQUIPMENT,
            TECHNOLOGY_DISTRIBUTORS,
            CONSUMER_ELECTRONICS
        )

        val semiconductorTech = setOf(SEMICONDUCTORS)

        val educationIndustries = setOf(EDUCATION_TRAINING_SERVICES)

        val traditionalFinancialIndustries = setOf(
            INSURANCE_SPECIALTY,
            INSURANCE_REINSURANCE,
            INSURANCE_PROPERTY_CASUALTY,
            INSURANCE_LIFE,
            INSURANCE_DIVERSIFIED,
            INSURANCE_BROKERS,
            FINANCIAL_MORTGAGES,
            FINANCIAL_DIVERSIFIED,
            FINANCIAL_CONGLOMERATES,
            BANKS_REGIONAL,
            BANKS_DIVERSIFIED,
            BANKS
        )

        val generalHealthcareIndustries = setOf(
            MEDICAL_SPECIALTIES,
            MEDICAL_PHARMACEUTICALS,
            MEDICAL_INSTRUMENTS_SUPPLIES,
            MEDICAL_HEALTHCARE_PLANS,
            MEDICAL_HEALTHCARE_INFORMATION_SERVICES,
            MEDICAL_EQUIPMENT_SERVICES,
            MEDICAL_DISTRIBUTION,
            MEDICAL_DIAGNOSTICS_RESEARCH,
            MEDICAL_DEVICES,
            MEDICAL_CARE_FACILITIES,
            DRUG_MANUFACTURERS_SPECIALTY_GENERIC,
            DRUG_MANUFACTURERS_GENERAL
        )

        val noInventoryIndustries = setOf(
            BIOTECHNOLOGY,
            SHELL_COMPANIES
        ) + stableCommIndustries + growthCommIndustries + traditionalFinancialIndustries + capitalMarketsFinancialIndustries + realEstateIndustries + softwareAndServicesTech + utilitiesIndustries + educationIndustries
    }
}